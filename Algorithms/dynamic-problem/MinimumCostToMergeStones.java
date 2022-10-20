/**
 * Created on:  Aug 09, 2020
 * Questions: https://leetcode.com/problems/minimum-cost-to-merge-stones/
 */
public class MinimumCostToMergeStones {

    static int max = 999999;

    public static void main(String[] args) {
        System.out.println(mergeStones(new int[]{3, 2, 4, 1}, 2));
    }

    public static int mergeStones(int[] stones, int k) {
        int len = stones.length;
        if ((len - 1) % (k - 1) != 0) {
            return -1;
        }
        int[] prefixSum = new int[len + 1];
        int i;
        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }
//        dp[a][b][c] = Cost of merging 'c' piles of stones from index 'a' to 'b'
        Integer[][][] dp = new Integer[len + 1][len + 1][k + 1];
//        Cost of merging k stones into one pile.
        Integer result = helper(prefixSum, 1, len, 1, k, dp);
        return result == null || result == max ? -1 : result;
    }

    private static Integer helper(int[] prefixSum, int start, int end, int piles, int k, Integer[][][] dp) {
//        When your start and end are same and you have only one pile, the merge cost will be 0. Else it is not possible to merge.
        if (start == end) return piles == 1 ? 0 : max;
        if (dp[start][end][piles] != null) return dp[start][end][piles];
//        When you have many piles and you can merge only one pile, in that case teh cost of merge will be
        dp[start][end][piles] = max;
        if (piles == 1) {
//            Cost of merging when you can only make 1 pile out of many (start to end) stones, will be =
//              Cost of merging all stones into k piles + (remove teh starting stone prefix sum value, since we have done an extra merge)
            return dp[start][end][piles] = helper(prefixSum, start, end, k, k, dp) + prefixSum[end] - prefixSum[start - 1];
        }
//        Loop through all the values from start to end and find the min value.
        for (int i = start; i < end; i++) {
//            F(start, end, piles) -> best values to merge piles number of stones between start and end,
//              min value of [cost of merging stones from start to i stones in piles -1 + cost of merging stones from i+1 to end in 1 pile]
//              min( F(start, start+k, piles-1) + F(start+k+1, end, 1) )
            dp[start][end][piles] = Math.min(dp[start][end][piles], helper(prefixSum, start, i, piles - 1, k, dp) + helper(prefixSum, i + 1, end, 1, k, dp));
        }
        return dp[start][end][piles];
    }
}
