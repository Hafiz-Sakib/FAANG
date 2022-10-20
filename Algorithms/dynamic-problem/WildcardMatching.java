import java.util.Arrays;

public class WildcardMatching {
    public static void main(String[] args) {
        System.out.println("****************** Recursion ************************");
        System.out.println(isMatch("aa", "a") + " should be [false]");
        System.out.println(isMatch("aa", "*") + " should be [true]");
        System.out.println(isMatch("cb", "?a") + " should be [false]");
        System.out.println(isMatch("adceb", "*a*b") + " should be [true]");
        System.out.println(isMatch("acdcb", "a*c?b") + " should be [false]");
        System.out.println(isMatch("ho", "**ho") + " should be [true]");
        System.out.println("****************** DP ************************");
        System.out.println(isMatch_dp("aa", "a") + " should be [false]");
        System.out.println(isMatch_dp("aa", "*") + " should be [true]");
        System.out.println(isMatch_dp("cb", "?a") + " should be [false]");
        System.out.println(isMatch_dp("adceb", "*a*b") + " should be [true]");
        System.out.println(isMatch_dp("acdcb", "a*c?b") + " should be [false]");
        System.out.println(isMatch_dp("ho", "**ho") + " should be [true]");
        System.out.println("****************** Revision ************************");
        System.out.println(isMatch_dp2("aa", "a") + " should be [false]");
        System.out.println(isMatch_dp2("aa", "*") + " should be [true]");
        System.out.println(isMatch_dp2("cb", "?a") + " should be [false]");
        System.out.println(isMatch_dp2("adceb", "*a*b") + " should be [true]");
        System.out.println(isMatch_dp2("acdcb", "a*c?b") + " should be [false]");
        System.out.println(isMatch_dp2("ho", "**ho") + " should be [true]");
    }

    public static boolean isMatch_dp2(String s, String p) {
        if (s == null && p == null) return true;
        if (s == null || p == null) return false;
        int rows = p.length(), cols = s.length();
        boolean[][] dp = new boolean[rows + 1][cols + 1];
        dp[0][0] = true;
//        Fill all the
        for (int row = 1; row <= rows; row++) {
            if (p.charAt(row - 1) == '*') {
                Arrays.fill(dp[row], true);
            } else break;
        }
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                char pChar = p.charAt(row - 1), sChar = s.charAt(col - 1);
                if (pChar == sChar || pChar == '?') {
                    dp[row][col] = dp[row - 1][col - 1] || (row == 1 && col == 1);
                } else if (pChar == '*') {
//                    Then take value by either ignoring "*" or by ignoring cur value at s.
                    dp[row][col] = dp[row - 1][col] || dp[row][col - 1];
                } else {
                    dp[row][col] = false;
                }
            }
        }
//        System.out.println(Arrays.deepToString(dp));
        return dp[rows][cols];
    }

    //    Solution: https://www.youtube.com/watch?v=3ZDZ-N0EPV0
    public static boolean isMatch_dp(String s, String p) {
        if (s == null && p == null) return true;
        if (s == null || p == null) return false;
        int sLen = s.length(), pLen = p.length();
        // base cases
        if (p.equals(s) || p.equals("*")) return true;
        if (p.isEmpty() || s.isEmpty()) return false;

        // init all matrix except [0][0] element as False
        boolean[][] d = new boolean[pLen + 1][sLen + 1];
        d[0][0] = true;

        // DP compute
        for (int pIdx = 1; pIdx < pLen + 1; pIdx++) {
            // the current character in the pattern is '*'
            if (p.charAt(pIdx - 1) == '*') {
                int sIdx = 1;
                // d[p_idx - 1][s_idx - 1] is a string-pattern match
                // on the previous step, i.e. one character before.
                // Find the first idx in string with the previous math.
                while ((!d[pIdx - 1][sIdx - 1]) && (sIdx < sLen + 1)) sIdx++;
                // If (string) matches (pattern),
                // when (string) matches (pattern)* as well
                d[pIdx][sIdx - 1] = d[pIdx - 1][sIdx - 1];
                // If (string) matches (pattern),
                // when (string)(whatever_characters) matches (pattern)* as well
                while (sIdx < sLen + 1) d[pIdx][sIdx++] = true;
            }
            // the current character in the pattern is '?'
            else if (p.charAt(pIdx - 1) == '?') {
                for (int sIdx = 1; sIdx < sLen + 1; sIdx++) {
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1];
                }
            }
            // the current character in the pattern is not '*' or '?'
            else {
                for (int sIdx = 1; sIdx < sLen + 1; sIdx++) {
                    // Match is possible if there is a previous match
                    // and current characters are the same
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1] &&
                            (p.charAt(pIdx - 1) == s.charAt(sIdx - 1));
                }
            }
        }
        return d[pLen][sLen];
    }

    public static boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0;
        int starIdx = -1, sTmpIdx = -1;

        while (sIdx < sLen) {
            // If the pattern caracter = string character
            // or pattern character = '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                ++sIdx;
                ++pIdx;
            }
            // If pattern character = '*'
            else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                // Check the situation
                // when '*' matches no characters
                starIdx = pIdx;
                sTmpIdx = sIdx;
                ++pIdx;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was no '*' character in pattern
            else if (starIdx == -1) {
                return false;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was '*' character in pattern before
            else {
                // Backtrack: check the situation
                // when '*' matches one more character
                pIdx = starIdx + 1;
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
            }
        }

        // The remaining characters in the pattern should all be '*' characters
        for (int i = pIdx; i < pLen; i++) {
            if (p.charAt(i) != '*') return false;
        }
        return true;
    }
}
