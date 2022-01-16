package queue;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class MyArrayDequeue<T> {
    private Object[] elements;

    private int head;

    private int tail;

    private static final int DEFAULT_QUEUE_CAPACITY = 8;

    public MyArrayDequeue() {
        elements = new Object[DEFAULT_QUEUE_CAPACITY];
    }

    public void pushBack(T t) {
        elements[tail] = t;
        if ((tail = inc(tail, elements.length)) == head) {
            doubleCapacity();
        }
    }

    public void pushFront(T t) {
        elements[head = (dec(head, elements.length))] = t;
        if (head == tail) {
            doubleCapacity();
        }
    }

    public T popBack() {
        T res = (T) elements[tail = dec(tail, elements.length)];
        elements[tail] = null;
        return res;
    }

    public T popFront() {
        T res = (T) elements[head];
        elements[head] = null;
        head = inc(head, elements.length);
        return res;
    }

    private void doubleCapacity() {
        int h = head;
        int n = elements.length;
        // [head:n - 1]的元素个数
        int r = n - h;
        int newCapacity = n << 1;
        if (newCapacity < 0) {
            throw new IllegalStateException("sorry deque is too big!");
        }
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, h, newElements, 0, r);
        System.arraycopy(elements, 0, newElements, r, h);
        elements = newElements;
        head = 0;
        tail = n;
    }

    private int inc(int i, int length) {
        if (++i >= length) {
            i = 0;
        }
        return i;
    }

    public int dec(int i, int length) {
        if (--i < 0) {
            i = length - 1;
        }
        return i;
    }


    public int size() {
        if (tail - head < 0) {
            return tail - head + elements.length;
        }
        return tail;
    }

    public boolean empty() {
        return head == tail;
    }

    public static void main(String[] args) {
        final MyArrayDequeue<Integer> q = new MyArrayDequeue<>();
        for (int i = 0; i < 10; i++) {
            q.pushBack(i);
        }
        while (!q.empty()) {
            System.out.println(q.popFront());
        }
    }
}
