package sort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CountSort {
    /**
     * 计数排序
     *
     * @param arr 需要排序的数组
     * @param min 数组中的最小值
     * @param max 数组中的最大值
     */
    public static void sort(Integer[] arr, int min, int max) {
        int[] count = new int[max - min + 1];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                arr[index++] = i;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = IntStream.generate(() -> (int) (Math.random() * 1000))
                .limit(100000)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] std = arr.clone();
        Arrays.sort(std);
        sort(arr, 0, 999);
        System.out.println(Arrays.equals(std, arr));
    }
}
