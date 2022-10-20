/**
 * Created on:  May 21, 2020
 * Questions: https://leetcode.com/problems/count-square-submatrices-with-all-ones/
 */
public class CountSquareSubmatriceswithAllOnes {
    public static void main(String[] args) {
        System.out.println(countSquares(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 1, 0}, {1, 1, 1}, {1, 1, 0}}) + " should be [8].");
        System.out.println(countSquares(new int[][]{
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 0}
        }) + " should be [7].");
        System.out.println(countSquares(new int[][]{
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1}
        }) + " should be [15].");
    }

    //    Time: O(n^3), Space: O(1)
    public static int countSquares_rev1(int[][] matrix) {
        int count = 0, rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        for (int row = 0; row < rows; row++) {
            int pre = 0;
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == 1) {
                    matrix[row][col] = pre += 1;
                    count++;
                } else {
                    pre = 0;
                }
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int rowOnes = matrix[row][col];
                for (int k = 1; k < rowOnes && row + k < rows; k++) {
                    rowOnes = Math.min(rowOnes, matrix[row + k][col]);
                    if (rowOnes >= k + 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    //    Time: O(n^2), Space: O(n^2)
    public static int countSquares(int[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[][] dp = new int[rows + 1][cols + 1];
        int op = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int min = Math.min(dp[row][col], Math.min(dp[row + 1][col], dp[row][col + 1]));
                dp[row + 1][col + 1] = matrix[row][col] == 0 ? 0 : min + matrix[row][col];
                op += dp[row + 1][col + 1];
            }
        }
        return op;
    }
}
