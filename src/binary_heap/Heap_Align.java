package binary_heap;

import utils.AlgorithmUtil;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-19
 */
public class Heap_Align {

    /**
     * 在二叉堆中插入一个新值, 默认按小根堆插入
     *
     * @param arr   需要插入的二叉堆数组
     * @param index 需要插入的元素位置
     */
    public static void heapInsert(int[] arr, int index, boolean smaller) {
        // 如果当前元素 < 父元素, 就与之交换, 直到index == 0, parentOf(0)将返回0, 与自己比 false 退出循环
        while ((smaller && arr[index] < arr[parentOf(index)])
                || (!smaller && arr[index] > arr[parentOf(index)])) {
            AlgorithmUtil.swap(arr, index, parentOf(index));
            index = parentOf(index);
        }
    }

    /**
     * 从index索引位置, 向下堆化, 默认是小根堆
     *
     * @param arr      二叉堆数组
     * @param index    当前索引
     * @param heapSize 堆大小
     */
    public static void heapify(int[] arr, int index, int heapSize, boolean smaller) {
        int left = leftOf(index);
        while (left < heapSize) {
            // 二叉堆只保证每次弹根节点时都是满足大根堆或小根堆, 并不保证子节点的有序性(左子节点与右子节点的关系未知)
            // 找到左右子节点中更小的那个节点
            int smallerChildIdx = left + 1 < heapSize
                    && ((smaller && arr[left + 1] < arr[left]) || (!smaller && arr[left + 1] > arr[left]))
                    ? left + 1 : left;
            // 更小的子节点与当前节点比较, 若当前节点更小(小根堆), 停止循环, 否则交换, 继续循环
            int smallerIdx = (smaller && arr[smallerChildIdx] < arr[index])
                    || (!smaller && arr[smallerChildIdx] > arr[index])
                    ? smallerChildIdx : index;
            if (smallerIdx == index) break;
            AlgorithmUtil.swap(arr, smallerIdx, index);
            index = smallerIdx;
            left = leftOf(index);
        }
    }

    public static int parentOf(int index) {
        return (index - 1) / 2;
    }

    public static int leftOf(int index) {
        return index * 2 + 1;
    }

    static class BinaryHeap {
        private int[] elements;

        // 是否是小根堆, 默认true
        private boolean smaller = true;

        private int size;

        private static final int DEFAULT_CAPACITY = 8;

        public BinaryHeap() {
            elements = new int[DEFAULT_CAPACITY];
        }

        public BinaryHeap(boolean smaller) {
            this();
            this.smaller = smaller;
        }

        public void push(int item) {
            // 满了就扩容
            if (size == elements.length - 1) {
                doubleCapacity();
            }
            elements[size] = item;
            heapInsert(elements, size++, smaller);
        }

        private void doubleCapacity() {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }

        public int pop() {
            if (size == 0) {
                throw new NoSuchElementException("heap is empty!");
            }
            AlgorithmUtil.swap(elements, 0, size - 1);
            int res = elements[size - 1];
            heapify(elements, 0, --size, smaller);
            return res;
        }

        public boolean empty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }

    public static void main(String[] args) {
        final BinaryHeap heap = new BinaryHeap(true);
        for (int i = 100; i > -1; i--) {
            heap.push(i);
        }
        while (!heap.empty()) {
            System.out.println(heap.pop());
        }
    }
}