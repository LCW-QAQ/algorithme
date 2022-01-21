package binary_heap;

import utils.AlgorithmUtil;

import java.util.*;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-21
 */
public class HeapGreater_Align<T> {
    private ArrayList<T> heap;

    private HashMap<T, Integer> indexMap;

    private Comparator<T> cmp;

    public HeapGreater_Align(Comparator<T> cmp) {
        this.cmp = cmp;
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    public void push(T t) {
        heap.add(t);
        final int index = heap.size() - 1;
        indexMap.put(t, index);
        heapPush(index);
    }

    public T pop() {
        final T res = heap.get(0);
        heapSwap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        indexMap.remove(res);
        heapify(0);
        return res;
    }

    /**
     * 将index索引向向下堆化
     */
    private void heapify(int index) {
        int left = leftOf(index);
        while (left < heap.size()) {
            int smallerChildIdx = left + 1 < heap.size()
                    && cmp.compare(heap.get(left + 1), heap.get(left)) < 0
                    ? left + 1 : left;
            int smallerIdx = cmp.compare(heap.get(smallerChildIdx), heap.get(index)) < 0 ? smallerChildIdx : index;
            if (smallerIdx == index) break;
            heapSwap(smallerIdx, index);
            index = smallerIdx;
            left = leftOf(index);
        }
    }

    private void heapPush(int index) {
        while (cmp.compare(heap.get(index), heap.get(parentOf(index))) < 0) {
            heapSwap(index, parentOf(index));
            index = parentOf(index);
        }
    }

    public void remove(T t) {
        // 获取要删除的那一项的索引
        final Integer rmIdx = indexMap.get(t);
        indexMap.remove(t);
        // 获取并删除最后一项
        final T lastItem = heap.get(heap.size() - 1);
        heap.remove(heap.size() - 1);
        // 需要删除不是最后一项
        if (cmp.compare(lastItem, t) != 0) {
            // 将需要出的那一项直接替换为最后一项
            heap.set(rmIdx, lastItem);
            indexMap.put(lastItem, rmIdx);
            // 将rmIdx正确堆化
            resign(rmIdx);
        }
    }

    /**
     * 将给定索引, 正确堆化
     * @param index
     */
    private void resign(Integer index) {
        heapPush(index);
        heapify(index);
    }

    public boolean contains(T t) {
        return indexMap.containsKey(t);
    }

    public T peek() {
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean empty() {
        return heap.isEmpty();
    }

    /**
     * 在堆中交换a,b索引位置的值, 同时更新反向索引表
     */
    public void heapSwap(int a, int b) {
        // 先交换索引表中的索引, 防止heap.get 失效
        indexMap.put(heap.get(a), b);
        indexMap.put(heap.get(b), a);
        Collections.swap(heap, a, b);
    }

    /**
     * 获取index索引位置的父节点索引
     */
    private int parentOf(int index) {
        return (index - 1) / 2;
    }

    /**
     * 获取index索引位置的左子节点索引
     */
    private int leftOf(int index) {
        return index * 2 + 1;
    }

    public static void main(String[] args) {
        final HeapGreater_Align<Integer> heap = new HeapGreater_Align<Integer>(Comparator.naturalOrder());
        for (int i = 99; i > -1; i--) {
            heap.push(i);
        }
        for (int i = 0; i < 10; i++) {
            heap.remove(i);
        }
        while (!heap.empty()) {
            System.out.println(heap.peek());
            heap.pop();
        }
    }
}