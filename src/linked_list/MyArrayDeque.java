package linked_list;

public class MyArrayDeque<T> {
    private Object[] elements;

    private int head;

    private int tail;

    private final int MIN_INITIAL_CAPACITY = 8;

    public MyArrayDeque() {
        elements = new Object[MIN_INITIAL_CAPACITY];
    }

    public void pushBack(T t) {
        elements[tail] = t;
        // 0000 0001 0010 0011 0100
        //   0    1    2   3     4
        if ((tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
    }

    public void pushFront(T t) {
        // 6 & 7
        // 0 1 2 3 4 5 6 7
        //           E H
        elements[head = (head - 1) & (elements.length - 1)] = t;
        if (head == tail)
            doubleCapacity();
    }

    public T popBack() {
        int newTail = (tail - 1) & (elements.length - 1);
        T result = (T) elements[newTail];
        elements[newTail] = null;
        tail = newTail;
        return result;
    }

    public T popFirst() {
        int newHead = (head + 1) & (elements.length - 1);
        T result = (T) elements[head];
        elements[head] = null;
        head = newHead;
        return result;
    }

    public void doubleCapacity() {
        int h = head;
        int n = elements.length;
        /*
        扩容的时候一定是满了, 先拷贝head到数组结尾的元素, 再拷贝0到h的元素
         */
        // head到数组结尾的元素个数
        int r = n - h;
        int newLen = n << 1;
        if (newLen < 0) {
            throw new IllegalStateException("deque is too big");
        }
        Object[] newArr = new Object[newLen];
        // 拷贝head-len
        System.arraycopy(elements, h, newArr, 0, r);
        // 拷贝0-head
        System.arraycopy(elements, 0, newArr, r, h);
        elements = newArr;
        head = 0;
        tail = n;
    }
}