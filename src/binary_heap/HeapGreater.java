package binary_heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapGreater<T extends HeapGreater.Wrapper<?>> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private Comparator<T> cmp;
    private int heapSize;

    public HeapGreater(Comparator<T> cmp) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        this.cmp = cmp;
    }

    /**
     * 向堆中添加一个元素
     *
     * @param t t元素
     */
    public void push(T t) {
        heap.add(t);
        indexMap.put(t, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public T peek() {
        return heap.get(0);
    }

    public int size() {
        return heapSize;
    }

    public boolean empty() {
        return heapSize == 0;
    }

    public boolean contains(T t) {
        return indexMap.containsKey(t);
    }

    public void remove(T t) {
        T lastItem = heap.get(heapSize - 1);
        Integer removeIndex = indexMap.get(t);
        indexMap.remove(t);
        heap.remove(--heapSize);
        if (t != lastItem) {
            heap.set(removeIndex, lastItem);
            indexMap.put(lastItem, removeIndex);
            resign(removeIndex);
        }
    }

    /**
     * 将给定索引的元素, 正确的堆化
     * @param index 索引
     */
    private void resign(Integer index) {
        heapInsert(index);
        heapify(index);
    }

    /**
     * 将index位置, 向上堆化
     *
     * @param index 索引
     */
    private void heapInsert(int index) {
        while (cmp.compare(heap.get(index), heap.get(parent(index))) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    /**
     * 将index位置, 向下堆化
     *
     * @param index 索引
     */
    private void heapify(int index) {
        int left = left(index);
        while (left < heapSize) {
            int smaller = right(index) < heapSize && cmp.compare(heap.get(right(index)), heap.get(left)) < 0
                    ? right(index) : left;
            smaller = cmp.compare(heap.get(index), heap.get(smaller)) < 0 ? index : smaller;
            if (smaller == index) break;
            swap(index, smaller);
            index = smaller;
            left = left(index);
        }
    }

    /**
     * 交换堆和索引表中i, j位置的元素
     *
     * @param i 索引i
     * @param j 索引j
     */
    private void swap(int i, int j) {
        T oi = heap.get(i);
        T oj = heap.get(j);
        heap.set(i, oj);
        heap.set(j, oi);
        indexMap.put(oi, j);
        indexMap.put(oj, i);
    }

    /**
     * 获取当前节点的父节点索引
     *
     * @param index 当前节点索引
     * @return 父节点索引
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * 获取当前节点的左子节点索引
     *
     * @param index 当前节点
     * @return 左子节点索引
     */
    private int left(int index) {
        return index * 2 + 1;
    }

    /**
     * 获取当前节点的右子节点索引
     *
     * @param index 当前节点
     * @return 右子节点索引
     */
    private int right(int index) {
        return index * 2 + 2;
    }

    static class Wrapper<T> {
        private T value;

        public Wrapper(T value) {
            this.value = value;
        }

        public T unwrap() {
            return value;
        }

        public static <T> Wrapper<T> wrap(T value) {
            return new Wrapper<>(value);
        }

        @Override
        public String toString() {
            return "Wrapper{" +
                    "value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        HeapGreater<Wrapper<Integer>> heap = new HeapGreater<>((o1, o2) -> o2.unwrap() - o1.unwrap());
        for (int i = 0; i < 100; i++) {
            Wrapper<Integer> item = Wrapper.wrap(i);
            heap.push(item);
        }
        for (int i = 0; i < 100; i++) {
            System.out.print(heap.pop().unwrap() + " ");
        }
    }
}