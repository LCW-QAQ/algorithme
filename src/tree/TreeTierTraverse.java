package tree;

import java.util.ArrayDeque;

public class TreeTierTraverse {
    public static void tierTraverse(Node<?> root) {
        if (root == null) return;

        ArrayDeque<Node<?>> deque = new ArrayDeque<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            Node<?> node = deque.poll();
            System.out.println(node);
            if (node.left != null) {
                deque.offer(node.left);
            }
            if (node.right != null) {
                deque.offer(node.right);
            }
        }
    }
}