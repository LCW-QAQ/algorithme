package binary_heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-19
 */
public class ArrayToOrderMovedLessThanK_Align {
    /**
     * 给定同一个数组, 这个数组想要变成有序的, 每个位置的数, 最多不移动超过k个
     * 请给出一种高效的排序方式
     *
     * @param arr 数组
     * @param k   想要边有序, 每个位置的数最多移动k个
     */
    public static void sort(Integer[] arr, int k) {
        final PriorityQueue<Integer> q = new PriorityQueue<>();
        // 每个位置的数最多移动k个, 就变成有序的了, 也就是说整个数组的最小值一定在[0, k]之中
        for (int i = 0; i <= k; i++) {
            q.offer(arr[i]);
        }
        arr[0] = q.poll();
        int index = 1;
        for (int i = k + 1; i < arr.length; i++) {
            q.offer(arr[i]);
            arr[index++] = q.poll();
        }
        while (!q.isEmpty()) {
            arr[index++] = q.poll();
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {4, 5, 1, 3, 2, 7};
        sort(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
