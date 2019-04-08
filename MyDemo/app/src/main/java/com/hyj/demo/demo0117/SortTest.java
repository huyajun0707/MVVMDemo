package com.hyj.demo.demo0117;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/1 9:20
 * @description :
 * =========================================================
 */
public class SortTest {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 0, 5, 4, 3, 9, 8, 6, 7};
//        shellSort(nums);
//        insertSort(nums);
//        bubbleSort(nums);
        quickSort(nums, 0, nums.length - 1);
//        selectSort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + ",");
        }


    }

    /**
     * 希尔排序
     * 不断对数组进行分组
     * 分别进行插入排序
     *
     * @param nums
     */

    public static void shellSort(int[] nums) {
        for (int gap = nums.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < nums.length; i++) {
                int j = i;
                while (j - gap >= 0 && nums[j] < nums[j - gap]) {
                    swap(nums, j, j - gap);
                    j -= gap;
                }
            }
        }

    }


    private static void swap(int[] nums, int j, int i) {
        nums[j] = nums[j] + nums[i];
        nums[i] = nums[j] - nums[i];
        nums[j] = nums[j] - nums[i];
    }

    /**
     * 插入排序
     *
     * @param numbers
     */
    public static void insertSort(int[] numbers) {
        int size = numbers.length;
        int temp = 0;
        int j = 0;
        for (int i = 0; i < size; i++) {
            temp = numbers[i];
            //假如temp比前面的值小，则将前面的值后移
            for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
                numbers[j] = numbers[j - 1];
            }
            numbers[j] = temp;
        }
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    public static void quickSort(int[] nums, int low, int high) {
        int start = low;
        int end = high;
        int key = nums[low];

        while (end > start) {
            //从后往前比较
            while (end > start && nums[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            nums[start] = nums[end];
            //从前往后比较
            while (end > start && nums[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            nums[end] = nums[start];
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        nums[start] = key;
        //递归
        if (start > low) quickSort(nums, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
        if (end < high) quickSort(nums, start + 1, high);//右边序列。从关键值索引+1到最后一个

    }

    /**
     * 选择排序
     *
     * @param nums
     */
    public static void selectSort(int[] nums) {
        int temp;
        for (int i = 0; i < nums.length; i++) {
            int k = i;   //待确定的位置
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[j] < nums[k]) {
                    k = j;
                }
            }
            //交换两个数
            temp = nums[i];
            nums[i] = nums[k];
            nums[k] = temp;
        }
    }

    /**
     * 归并排序
     */


}
