package com.hyj.demo.demo0117;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/11 14:56
 * @description :
 * =========================================================
 */
public class SortTest2 {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 8, 1, 3, 6, 2, 9, 4, 7};
        selectSort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
        }

    }

    public static void bubbleSort(int[] nums) {
        //        for (int i = 0; i < nums.length; i++) {
        //            for (int j = 0; j < nums.length - i - 1; j++) {
        //                if (nums[j] > nums[j + 1]) {
        //                    int temp = nums[j];
        //                    nums[j] = nums[j + 1];
        //                    nums[j + 1] = temp;
        //                }
        //            }
        //        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }

    }

    /**
     * 从第i位开始比，后面的数和前面的比，如果后面的数大于前面的数，
     * 则前面的数后移一位，直到没有大于的时候，则当前的位置填入一开始的i值
     *
     * @param nums
     */
    public static void insertSort(int[] nums) {
        //        int temp;
        //        int j;
        //        for (int i = 0; i < nums.length; i++) {
        //            temp = nums[i];
        //            for (j = i; j > 0 && temp < nums[j - 1]; j--) {
        //                nums[j] = nums[j - 1];
        //            }
        //            nums[j] = temp;
        //        }
        int temp;
        int j;
        for (int i = 0; i < nums.length; i++) {
            temp = nums[i];
            for (j = i; j > 0 && temp < nums[j - 1]; j--) {
                nums[j] = nums[j - 1];
            }
            nums[j] = temp;
        }

    }

    /**
     * 找出最小的值 放在第i位
     *
     * @param nums
     */
    public static void selectSort(int[] nums) {
        //        for (int i = 0; i < nums.length; i++) {
        //            int k = i;
        //            for (int j = nums.length - 1; j > i; j--) {
        //                if (nums[j] < nums[k])
        //                    k = j;
        //            }
        //
        //            int temp = nums[i];
        //            nums[i] = nums[k];
        //            nums[k] = temp;
        //
        //
        //        }

        for (int i = 0; i < nums.length; i++) {
            int k = i;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[k]) {
                    k = j;
                }
            }
            int temp = nums[i];
            nums[i] = nums[k];
            nums[k] = temp;
        }

    }

}
