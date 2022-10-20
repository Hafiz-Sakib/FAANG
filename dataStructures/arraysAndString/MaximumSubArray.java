/*
https://leetcode.com/problems/maximum-subarray/submissions/
 */
public class MaximumSubArray {
    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray(new int[]{-1, -2, -3, -4, -5, -6, -7, -8, -9, -10}));
    }

    //    Loop through the current array.
//    If the sum of max and the current value is greater then max then set the sum to max.
//    Else set the max value to the current value.
//    And get the max value of the max and the previous result.
    public static int maxSubArray(int[] nums) {
        int result = nums[0], max = nums[0];
        for (int i = 1; i <= nums.length - 1; i++) {
            int cur = nums[i];
            max = Math.max(max + cur, cur);
            result = Math.max(max, result);
        }
        return result;
    }
}
