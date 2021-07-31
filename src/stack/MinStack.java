package stack;

import java.util.NoSuchElementException;
import java.util.Stack;

public class MinStack<T extends Comparable<T>> {
    private Stack<T> dataStack;

    private Stack<T> minStack;

    public MinStack() {
        dataStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(T t) {
        if (minStack.size() > 0) {
            T top = minStack.peek();
            // top比新元素大, 新元素入min栈
            if (top.compareTo(t) > 0) {
                minStack.push(t);
            } else {
                minStack.push(top);
            }
        } else {
            minStack.push(t);
        }
        dataStack.push(t);
    }

    public T pop() {
        if (dataStack.size() == 0) {
            throw new NoSuchElementException("stack empty");
        }
        minStack.pop();
        return dataStack.pop();
    }

    public static void main(String[] args) {
        MinStack<Integer> stack = new MinStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }
}