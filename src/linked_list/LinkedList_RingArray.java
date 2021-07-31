package linked_list;

public class LinkedList_RingArray<T> {
    private Object[] elements;

    private int head;

    private int tail;

    private int size;

    public LinkedList_RingArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must greater then two");
        }
        elements = new Object[capacity];
    }

    public void push(T t) {
        if (size == elements.length) {
            throw new IndexOutOfBoundsException("deque full");
        }
        size++;
        elements[tail] = t;
        tail = nextIndex(tail);
    }

    public T pop() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("deque empty");
        }
        size--;
        T result = (T) elements[head];
        head = nextIndex(head);
        return result;
    }

    private int nextIndex(int i) {
        return i < elements.length - 1 ? i + 1 : 0;
    }

    public static void main(String[] args) {
        LinkedList_RingArray<Integer> queue = new LinkedList_RingArray<Integer>(5);
        for (int i = 0; i < 5; i++) {
            queue.push(i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.pop());
        }
    }
}
