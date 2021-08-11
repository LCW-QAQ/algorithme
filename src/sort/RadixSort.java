package sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class RadixSort {
    /**
     * 基数排序
     *
     * @param arr 需要排序的数组
     */
    public static void sort(Integer[] arr) {
        if (arr.length < 2) return;
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 将数组[l..r]区间排有序
     *
     * @param arr     数组
     * @param l       左边界
     * @param r       右边界
     * @param maxBits 数组最大值的位数
     */
    private static void radixSort(Integer[] arr, int l, int r, int maxBits) {
        Integer[] temp = new Integer[r - l + 1];
        for (int d = 1; d <= maxBits; d++) {
            int[] count = new int[10];
            // 遍历[l..r]区间上, 从右向左第d位的数, 将他对count的值 +1
            for (int i = l; i <= r; i++) {
                count[getDigit(arr[i], d)]++;
            }
            // 前缀和
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }
            // 从后向前遍历, 我们可以保证后面的一定出现在靠后的索引
            for (int i = r; i >= l; i--) {
                temp[--count[getDigit(arr[i], d)]] = arr[i];
            }
            System.arraycopy(temp, 0, arr, l, temp.length);
        }
    }

    /**
     * 计算给定数字, 从右向左第d位的值
     *
     * @param num 数字
     * @return num从右向左第d位的值
     */
    private static int getDigit(int num, int d) {
        return num / (int) Math.pow(10, d - 1) % 10;
    }

    /**
     * 返回给定数组中, 最大值的位数
     *
     * @param arr 数组
     * @return 数组中最大值的位数
     */
    private static int maxBits(Integer[] arr) {
        int max = Arrays.stream(arr).max(Comparator.naturalOrder()).get();
        int maxBits = 0;
        while (max != 0) {
            max /= 10;
            maxBits++;
        }
        return maxBits;
    }

    public static void main(String[] args) {
        Integer[] arr = IntStream.generate(() -> (int) (Math.random() * 1000))
                .limit(100)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] std = arr.clone();
        Arrays.sort(std);
        sort(arr);
        System.out.println(Arrays.equals(std, arr));
    }
}