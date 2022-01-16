package utils;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class AlgorithmUtil {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
