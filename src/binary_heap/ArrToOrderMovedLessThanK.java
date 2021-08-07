package binary_heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class ArrToOrderMovedLessThanK {
    /**
     * 给定同一个数组, 这个数组想要变成有序的, 每个位置的数, 最多不移动超过k个
     * 请给出一种高效的排序方式
     *
     * @param arr 数组
     * @param k   想要边有序, 每个位置的数最多移动k个
     */
    public static void sort(Integer[] arr, int k) {
        /*
        思路:
        题目告诉我们了数组想要排有序, 每个数移动不会超过k
        默认升序, 0索引上的数是最小值, 他只可能是0-k索引上的数
        因此将0-k推入小根堆, 然后弹出的值就是数组的最小值
        接下来每次加入k后面的数, 再弹出到arr中
        数组有n个数, 每次小根堆中只有k + 1个元素
        时间复杂度: O(n*logk) 在k比n小的多的时候, 效率更高
         */
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i <= k; i++) {
            heap.offer(arr[i]);
        }
        arr[0] = heap.poll();
        int index = 1;
        for (int i = k + 1; i < arr.length; i++) {
            heap.offer(arr[i]);
            arr[index++] = heap.poll();
        }
        while (!heap.isEmpty()) {
            arr[index++] = heap.poll();
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {4, 5, 1, 3, 2, 7};
        sort(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
