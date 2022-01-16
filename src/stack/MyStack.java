package stack;

import java.util.NoSuchElementException;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class MyStack<T> {
    private Object[] elements;

    private int size;

    private static final int DEFAULT_STACK_CAPACITY = 8;

    public MyStack() {
        elements = new Object[DEFAULT_STACK_CAPACITY];
    }

    MyStack(int size) {
        elements = new Object[size];
    }

    public void push(T t) {
        ensureCapacity();
        elements[size++] = t;
    }

    private void ensureCapacity() {
        if (size + 1 == elements.length) {
            Object[] newElements = new Object[elements.length << 1];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("stack is empty");
        }
        T res = (T) elements[size - 1];
        elements[--size] = null;
        return res;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public static void main(String[] args) {
        final MyStack<Integer> stack = new MyStack<Integer>(10);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }
}
