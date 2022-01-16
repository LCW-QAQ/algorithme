package stack;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 用队列实现栈, 队列不是双端队列
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-15
 */
public class StackByQueue_Align<T> {
    private Queue<T> queue = new ArrayDeque<>();

    private Queue<T> queueHelp = new ArrayDeque<>();

    public boolean push(T t) {
        return queue.offer(t);
    }

    public T pop() {
        while (queue.size() > 1) {
            queueHelp.offer(queue.poll());
        }
        final T res = queue.poll();
        Queue<T> temp = queue;
        queue = queueHelp;
        queueHelp = temp;
        return res;
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        final StackByQueue_Align<Integer> q = new StackByQueue_Align<>();
        for (int i = 0; i < 10; i++) {
            q.push(i);
        }

        while (!q.empty()) {
            System.out.println(q.pop());
        }
    }
}