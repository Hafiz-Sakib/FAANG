import java.util.*;
import java.util.stream.Collectors;

/*
A string S of lowercase letters is given.
We want to partition this string into as many parts as possible so that each letter appears in at most one part,
    and return a list of integers representing the size of these parts.
Example 1:
Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 */
public class PartitionLabels {
    public static void main(String[] args) {
        System.out.println(partitionLabels("ababcbacadefegdehijhklij"));
        System.out.println(partitionLabels("caedbdedda"));

        System.out.println(partitionLabels_rev2("ababcbacadefegdehijhklij"));
        System.out.println(partitionLabels_rev2("caedbdedda"));
    }

    public static List<Integer> partitionLabels_rev2(String s) {
        Map<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.putIfAbsent(c, new int[]{i, i});
            map.get(c)[1] = i;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((r1, r2) -> r1[0] - r2[0]);
        for (int[] range : map.values()) {
            pq.add(range);
        }
        List<int[]> expandedRanges = new ArrayList<>();
        int[] pre = pq.poll();
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (hasOverLap(pre, cur)) {
                pre[0] = Math.min(cur[0], pre[0]);
                pre[1] = Math.max(cur[1], pre[1]);
            } else {
                expandedRanges.add(pre);
                pre = cur;
            }
        }
        expandedRanges.add(pre);
        return expandedRanges.stream().map(range -> range[1] - range[0] + 1).collect(Collectors.toList());
    }

    private static boolean hasOverLap(int[] pre, int[] cur) {
//        return Math.max(pre[0], cur[0]) < Math.min(pre[1], cur[1]);
        return pre[1] > cur[0];
    }

    public static List<Integer> partitionLabels_revision(String S) {
        Map<Character, Pair> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            map.putIfAbsent(c, new Pair(i, c));
            map.get(c).end = i;
        }
        PriorityQueue<Pair> queue = new PriorityQueue<>((p1, p2) -> {
            if (p1.start == p2.start) return p2.getDiff() - p1.getDiff();
            return p1.start - p2.start;
        });
        queue.addAll(map.values());
        List<Integer> op = new ArrayList<>();
        while (!queue.isEmpty() && queue.size() >= 2) {
            Pair poll = queue.poll();
            Pair peek = queue.peek();
            if (poll.end >= peek.end) {
//                 if the next element is with in the poll.
//                 Then remove next and add poll pair
                queue.poll();
                queue.add(poll);
            } else if (poll.end > peek.start) {
//                 If second item is continuation of first item.
//                 Then extend the first one.
                poll.end = peek.end;
                queue.poll();
                queue.add(poll);
            } else {
                op.add(poll.getDiff());
            }
        }
        if (!queue.isEmpty()) op.add(queue.poll().getDiff());
        return op;
    }

    public static List<Integer> partitionLabels(String S) {
        List<String> output = new ArrayList<>();
        Map<Character, Integer> characterMap = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            characterMap.put(S.charAt(i), i);
        }

        int p1 = 0, p2 = 0;
        for (int i = 0; i < S.length(); ++i) {
            p1 = Math.max(p1, characterMap.get(S.charAt(i)));
            if (i == p1) {
                output.add(S.substring(p2, p1 + 1));
                p2 = i + 1;
            }
        }
//        System.out.println(output);
        return output.parallelStream().map(String::length).collect(Collectors.toList());
    }

    public static List<Integer> partitionLabels_optimal(String S) {
        int[] last = new int[26];
        for (int i = 0; i < S.length(); ++i)
            last[S.charAt(i) - 'a'] = i;

        int j = 0, anchor = 0;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < S.length(); ++i) {
            j = Math.max(j, last[S.charAt(i) - 'a']);
            if (i == j) {
                ans.add(i - anchor + 1);
                anchor = i + 1;
            }
        }
        return ans;
    }

    static class Pair {
        int start;
        int end;
        char c;

        Pair(int start, char c) {
            this.start = start;
            this.c = c;
        }

        int getDiff() {
            return end - start + 1;
        }

        public String toString() {
            return "" + c + " start =" + start + " end=" + end;
        }
    }
}
