package com.git.onedayrex.mdown.thread;

/**
 * Created by fuxiang.zhong on 2018/4/8.
 */
public class Solution {
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] result = new int[length];
        for (int i = 0; i < nums.length; i++) {
            int pos = (i + k) % length;
            result[pos] = nums[i];
        }
        System.out.print("[");
        for (int i=0;i<result.length;i++) {
            if (i == result.length - 1) {
                System.out.print(result[i]);
            } else {
                System.out.print(result[i]+",");
            }
        }
        System.out.print("]");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {1,2,3,4,5,6,7};
        solution.rotate(nums, 3);
    }
}
