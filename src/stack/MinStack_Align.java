package stack;

import java.util.Stack;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class MinStack_Align {
    private Stack<Integer> stack;

    private Stack<Integer> stackMin;

    MinStack_Align() {
        stack = new Stack<>();
        stackMin = new Stack<>();
    }

    public void push(int item) {
        if (stackMin.size() > 0) {
            stackMin.push(stackMin.peek() < item ? stackMin.peek() : item);
        } else {
            stackMin.push(item);
        }
        stack.push(item);
    }

    public int pop() {
        stackMin.pop();
        return stack.pop();
    }

    public int getMin() {
        return stackMin.peek();
    }

    public boolean empty() {
        return stack.empty();
    }

    public static void main(String[] args) {
        final MinStack_Align stack = new MinStack_Align();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        while (!stack.empty()) {
            System.out.printf("min: %s, pop: %s%n", stack.getMin(), stack.pop());
        }
    }
}