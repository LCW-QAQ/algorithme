package stack;

import java.util.ArrayDeque;

public class StackByQueue<T> {
    private ArrayDeque<T> deque = new ArrayDeque<>();

    private ArrayDeque<T> help = new ArrayDeque<>();

    public void pushBack(T t) {
        deque.offer(t);
    }

    public T popBack() {
        while (deque.size() > 1) {
            help.offer(deque.poll());
        }
        T result = deque.poll();
        ArrayDeque<T> temp = deque;
        deque = help;
        help = temp;
        return result;
    }

    public static void main(String[] args) {
        StackByQueue<Integer> stack = new StackByQueue<>();
        for (int i = 0; i < 10; i++) {
            stack.pushBack(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(stack.popBack() + " ");
        }
    }
}
