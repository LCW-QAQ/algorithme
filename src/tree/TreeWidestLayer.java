package tree;

import java.util.ArrayDeque;
import java.util.Queue;

// 求二叉树上最宽的层有多少节点
public class TreeWidestLayer {

    /**
     * 求出二叉树最宽的一层有多少个节点
     * @param root 根节点
     * @return 最宽的一层有多少个节点
     */
    public static int widestLayer(Node<?> root) {
        if (root == null) return 0;

        Queue<Node<?>> queue = new ArrayDeque<>();
        queue.offer(root);
        int max = 0, curLevel = 0;
        Node<?> curEnd = root, nextEnd = null;
        while (!queue.isEmpty()) {
            Node<?> node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextEnd = node.right;
            }
            curLevel++;
            // 当前这个节点是这一层的最后一个节点
            if (node == curEnd) {
                max = Math.max(max, curLevel);
                curLevel = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }
}
