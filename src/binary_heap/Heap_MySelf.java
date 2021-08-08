package binary_heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class Heap_MySelf {
    /**
     * 将指定索引位置向上堆化
     *
     * @param arr   数组
     * @param index 索引
     */
    public static <T extends Comparable<T>> void heapInsert(T[] arr, int index) {
        while (arr[index].compareTo(arr[parent(index)]) < 0) {
            swap(arr, index, parent(index));
            index = parent(index);
        }
    }

    /**
     * 将指定索引位置向下堆化
     *
     * @param arr      数组
     * @param index    索引
     * @param heapSize 堆大小
     */
    public static <T extends Comparable<T>> void heapify(T[] arr, int index, int heapSize) {
        int left = left(index);
        while (left < heapSize) {
            int smaller = right(index) < heapSize && arr[right(index)].compareTo(arr[left]) < 0 ? right(index) : left;
            smaller = arr[index].compareTo(arr[smaller]) < 0 ? index : smaller;
            if (smaller == index) break;
            swap(arr, index, smaller);
            index = smaller;
            left = left(index);
        }
    }

    public static <T extends Comparable<T>> void heapSort(T[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    /**
     * 获取当前节点的父节点索引
     *
     * @param index 当前节点索引
     * @return 父节点索引
     */
    public static int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * 获取当前节点的左子节点索引
     *
     * @param index 当前节点
     * @return 左子节点索引
     */
    public static int left(int index) {
        return index * 2 + 1;
    }

    /**
     * 获取当前节点的右子节点索引
     *
     * @param index 当前节点
     * @return 右子节点索引
     */
    public static int right(int index) {
        return index * 2 + 2;
    }

    public static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        checkHeapSort();
    }

    public static void checkHeapSort() {
        Integer[] arr = IntStream.generate(() -> new Random().nextInt(10000))
                .limit(1000000)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] std = arr.clone();
        Arrays.sort(std, Comparator.reverseOrder());
        heapSort(arr);
        System.out.println(Arrays.equals(arr, std));
    }
}
