package sort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ShellSort {
    /**
     * 希尔排序
     *
     * @param arr 数组
     */
    public static void sort(Integer[] arr) {
        int h = 1;
        while (h <= arr.length / 3) h = 3 * h + 1;
        for (int gap = h; gap > 0; gap = (gap - 1) / 3) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j >= gap; j -= gap) {
                    if (arr[j] < arr[j - gap]) swap(arr, j, j - gap);
                }
            }
        }
    }

    public static void swap(Integer[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        Integer[] arr = IntStream.generate(() -> (int) (Math.random() * 1000))
                .limit(100000)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] std = arr.clone();
        Arrays.sort(std);
        sort(arr);
        System.out.println(Arrays.equals(std, arr));
    }
}
