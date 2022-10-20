import java.util.*;

/**
 * Created on:  Nov 13, 2020
 * Questions: https://leetcode.com/problems/stamping-the-sequence/
 */

public class StampingTheSequence {

    static List<Integer> result;

    public static void main(String[] args) {
        System.out.println("********************************* Solution 1 *****************************");
        System.out.println(Arrays.toString(movesToStamp("abc", "ababc")));
        System.out.println(Arrays.toString(movesToStamp("abca", "aabcaca")));
        System.out.println(Arrays.toString(movesToStamp("e", "eeeeeeeeee")));
        System.out.println(Arrays.toString(movesToStamp("k", "kkkkkkkkkkkkkkk")));
        System.out.println(Arrays.toString(movesToStamp("tkuq", "tkuqtkuqqttkuqq")));

        System.out.println("********************************* Solution 2 *****************************");
        System.out.println(Arrays.toString(movesToStamp_optimal("abc", "ababc")));
        System.out.println(Arrays.toString(movesToStamp_optimal("abca", "aabcaca")));
        System.out.println(Arrays.toString(movesToStamp_optimal("e", "eeeeeeeeee")));
        System.out.println(Arrays.toString(movesToStamp_optimal("k", "kkkkkkkkkkkkkkk")));
        System.out.println(Arrays.toString(movesToStamp_optimal("tkuq", "tkuqtkuqqttkuqq")));

        System.out.println("********************************* Solution 3 *****************************");
        System.out.println(Arrays.toString(movesToStamp_rev1("abc", "ababc")));
        System.out.println(Arrays.toString(movesToStamp_rev1("abca", "aabcaca")));
        System.out.println(Arrays.toString(movesToStamp_rev1("e", "eeeeeeeeee")));
        System.out.println(Arrays.toString(movesToStamp_rev1("k", "kkkkkkkkkkkkkkk")));
        System.out.println(Arrays.toString(movesToStamp_rev1("tkuq", "tkuqtkuqqttkuqq")));
    }

    public static int[] movesToStamp_rev1(String stamp, String target) {
        result = new ArrayList<>();
        char[] op = new char[target.length()];
        Arrays.fill(op, '?');
        helper(stamp, target, op, 0, 0, new LinkedList<>());
        return result.stream().mapToInt(val -> val).toArray();
    }

    static boolean helper(String stamp, String target, char[] op, int idx, int steps, LinkedList<Integer> soFar) {
        int tLen = target.length(), sLen = stamp.length();
        if (steps >= 10 * tLen) return false;
        if (idx == tLen) {
            if (String.valueOf(op).equals(target)) {
                result = new ArrayList<>(soFar);
                return true;
            }
            return false;
        } else if (target.charAt(idx) == op[idx]) {
            return helper(stamp, target, op, idx + 1, steps, soFar);
        } else {
//             Find out the char in stap that can match the current target char
            for (int i = 0; i < sLen && i <= idx && idx + (sLen - i) <= tLen; i++) {
                if (stamp.charAt(i) == target.charAt(idx)) {
                    int start = idx - i;
                    soFar.add(start);
                    char[] temp = Arrays.copyOf(op, tLen);
                    for (int k = 0; k < sLen; k++) {
                        temp[start++] = stamp.charAt(k);
                    }
                    if (helper(stamp, target, temp, idx + 1, steps + 1, soFar)) {
                        return true;
                    }
                    soFar.removeLast();
                }
            }
            return false;
        }
    }

    //    https://leetcode.com/problems/stamping-the-sequence/discuss/201546/12ms-Java-Solution-Beats-100
    public static int[] movesToStamp_optimal(String stamp, String target) {
        char[] s = stamp.toCharArray(), t = target.toCharArray();
        Queue<Integer> seq = new LinkedList<>();
        boolean[] visited = new boolean[t.length];
        int stars = 0;
        while (stars < t.length) {
            boolean doneReplace = false;
//            Loop through the target (of stamp sized substring) and try finding if stamp can be erased and changed to *.
            for (int i = 0; i <= t.length - s.length; i++) {
                if (!visited[i] & canBeFormedByStamp(t, i, s)) {
                    stars = replaceWithStar(t, i, s, stars);
                    doneReplace = visited[i] = true;
                    seq.add(i);
                    if (stars == t.length) {
//                        Then all the chars are replaced.
                        break;
                    }
                }
            }
            if (!doneReplace) return new int[0];
        }
        int[] result = new int[seq.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = seq.poll();
        }
        return result;
    }

    private static int replaceWithStar(char[] t, int idx, char[] s, int stars) {
        for (int i = 0; i < s.length; i++) {
            if (t[i + idx] == '*') continue;
            t[i + idx] = '*';
            stars++;
        }
        return stars;
    }

    private static boolean canBeFormedByStamp(char[] t, int idx, char[] s) {
        for (int i = 0; i < s.length; i++) {
            if (t[idx + i] == '*') continue;
            if (t[idx + i] == s[i]) continue;
            return false;
        }
        return true;
    }

    public static int[] movesToStamp(String stamp, String target) {
        int len = target.length(), level = 0;
        Queue<Stamp> queue = new LinkedList<>();
        String start = "?".repeat(len);
        queue.add(new Stamp(start, new ArrayList<>()));
        Set<String> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (level == 10 * len) break;
            for (int i = 0; i < size; i++) {
                Stamp poll = queue.poll();
                if (poll.val.equals(target)) return poll.seq.stream().mapToInt(val -> val).toArray();
                for (int j = 0; j < len; j++) {
                    String newString = getStampString(poll.val, j, stamp);
                    if (visited.add(newString)) {
                        List<Integer> seq = new ArrayList<>(poll.seq);
                        seq.add(j);
                        queue.add(new Stamp(newString, seq));
                    }
                }
            }
            level++;
        }
        return new int[0];
    }

    private static String getStampString(String str, int idx, String stamp) {
        char[] chars = str.toCharArray();
        int si = 0;
        for (int i = idx; i < chars.length && si < stamp.length(); i++) {
            chars[i] = stamp.charAt(si++);
        }
        return String.valueOf(chars);
    }

    static class Stamp {
        String val;
        List<Integer> seq;

        public Stamp(String val, List<Integer> seq) {
            this.val = val;
            this.seq = seq;
        }

        @Override
        public String toString() {
            return "Stamp{" +
                    "val='" + val + '\'' +
                    ", seq=" + seq +
                    '}';
        }
    }
}
