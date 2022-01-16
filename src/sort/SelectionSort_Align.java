package sort;

import utils.AlgorithmUtil;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class SelectionSort_Align {
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIdx] > arr[j]) {
                    minIdx = j;
                }
            }
            AlgorithmUtil.swap(arr, i, minIdx);
        }
    }

    public static void main(String[] args) {
        final int[] arr = Stream.generate(() -> new Random().nextInt(100))
                .limit(10)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(arr));
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
