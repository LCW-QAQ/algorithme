package stack;

import java.util.Stack;

/**
 * 用栈实现单端队列
 *
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class QueueByStack_Align<T> {
    private Stack<T> stack = new Stack<>();

    private Stack<T> stackHelp = new Stack<>();

    public boolean offer(T t) {
        stack.push(t);
        return true;
    }

    public T poll() {
        while (stack.size() > 1) {
            stackHelp.push(stack.pop());
        }
        final T res = stack.pop();
        while (!stackHelp.empty()) {
            stack.push(stackHelp.pop());
        }
        return res;
    }

    public boolean empty() {
        return stack.empty();
    }

    public static void main(String[] args) {
        final QueueByStack_Align<Integer> q = new QueueByStack_Align<>();
        for (int i = 0; i < 10; i++) {
            q.offer(i);
        }

        while (!q.empty()) {
            System.out.println(q.poll());
        }
    }
}
