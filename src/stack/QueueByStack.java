package stack;

import java.util.Stack;

public class QueueByStack<T> {
    private Stack<T> stack = new Stack<>();

    private Stack<T> help = new Stack<>();

    public void pushBack(T t) {
        stack.push(t);
    }

    public T popFront() {
        return transformPop();
    }

    public T transformPop() {
        while (!stack.isEmpty()) {
            help.push(stack.pop());
        }
        T result = help.pop();
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
        return result;
    }

    public boolean empty() {
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        QueueByStack<Integer> queue = new QueueByStack<>();
        for (int i = 0; i < 10; i++) {
            queue.pushBack(i);
        }
        while (!queue.empty()) {
            System.out.print(queue.popFront() + " ");
        }
    }
}
