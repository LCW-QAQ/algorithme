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
public class BubbleSort_Algin {
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    AlgorithmUtil.swap(arr, j, j - 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        final int[] arr = Stream.generate(() -> new Random().nextInt(100))
                .limit(10)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
