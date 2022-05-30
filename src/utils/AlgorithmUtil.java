package utils;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class AlgorithmUtil {

    private static final int DEFAULT_RANDOM_ARRAY_LENGTH = 100000;

    private static final int DEFAULT_RANDOM_MIN_VALUE = 0;

    private static final int DEFAULT_RANDOM_MAX_VALUE = 10000;

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] genRandomArray() {
        return genRandomArray(DEFAULT_RANDOM_ARRAY_LENGTH, DEFAULT_RANDOM_MIN_VALUE, DEFAULT_RANDOM_MAX_VALUE);
    }

    public static int[] genRandomArray(int length, int minVal, int maxVal) {
        final Random r = new Random();
        return Stream.generate(() -> minVal + r.nextInt(maxVal - minVal))
                .limit(length)
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
