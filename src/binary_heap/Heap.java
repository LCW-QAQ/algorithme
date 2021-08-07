package binary_heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class Heap {

    /**
     * 在堆中插入一个新值, 按小根堆插入
     *
     * @param arr   数组
     * @param index 插入数值的索引
     */
    public static void heapInsert(int[] arr, int index) {
        // 只要index 是小于(小根堆)他的父元素 就需要交换
        // 当index == 0 时, (index - 1) / 2 为 0, 自己跟自己比, false退出
        while (arr[index] < arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 在堆中插入一个新值, 按比较器插入
     *
     * @param arr   数组
     * @param index 插入元素的索引
     * @param cmp   比较器
     * @param <T>   泛型
     */
    public static <T> void heapInsert(T[] arr, int index, Comparator<T> cmp) {
        while (cmp.compare(arr[index], arr[(index - 1) / 2]) < 0) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 从index位置向下沉, 变为小跟堆
     *
     * @param arr      数组
     * @param index    需要heapify的索引
     * @param heapSize 当前堆的长度
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        // 左子节点
        int left = index * 2 + 1;
        while (left < heapSize) {
            // 更小的子节点的索引
            int smallerIdx = left + 1 < heapSize && arr[left + 1] < arr[left] ? left + 1 : left;
            // 更小的子节点跟自己比
            smallerIdx = arr[index] < arr[smallerIdx] ? index : smallerIdx;
            // 我更小不需要动
            if (smallerIdx == index) break;
            swap(arr, index, smallerIdx);
            index = smallerIdx;
            left = index * 2 + 1;
        }
    }

    public static <T> void heapify(T[] arr, int index, int heapSize, Comparator<T> cmp) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int smallerIdx = left + 1 < heapSize && cmp.compare(arr[left + 1], arr[left]) < 0 ? left + 1 : left;
            smallerIdx = cmp.compare(arr[index], arr[smallerIdx]) < 0 ? index : smallerIdx;
            if (smallerIdx == index) break;
            swap(arr, index, smallerIdx);
            index = smallerIdx;
            left = index * 2 + 1;
        }
    }

    /**
     * 堆排序
     *
     * @param arr 需要排序的数组
     */
    public static void heapSort(Integer[] arr) {
        // arr 会变成小根堆
        // 从上向下 O(n*logn)
        /*for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i, Comparator.reverseOrder());
        }*/
        // 从下向上 O(n)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length, Comparator.reverseOrder());
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize, Comparator.reverseOrder());
            swap(arr, 0, --heapSize);
        }
    }

    static class BinaryHeap {
        int[] elements;
        int heapSize;

        public BinaryHeap(int length) {
            elements = new int[length];
        }

        public void push(int item) {
            elements[heapSize] = item;
            heapInsert(elements, heapSize++);
        }

        public int pop() {
            swap(elements, 0, heapSize - 1);
            int result = elements[heapSize - 1];
            heapify(elements, 0, --heapSize);
            return result;
        }
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
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
                .limit(1000)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] std = arr.clone();
        Arrays.sort(std);
        heapSort(arr);
        System.out.println(Arrays.equals(std, arr));
    }

    public static void checkBinaryHeap() {
        int[] arr = IntStream.generate(() -> new Random().nextInt(100000))
                .limit(1000)
                .toArray();
        int[] std = arr.clone();
        Arrays.sort(std);
        BinaryHeap heap = new BinaryHeap(arr.length);
        Arrays.stream(arr).forEach(heap::push);
        int[] cmpArr = IntStream.generate(heap::pop).limit(arr.length).toArray();
        System.out.println(Arrays.equals(std, cmpArr));
    }
}