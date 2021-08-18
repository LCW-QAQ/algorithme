package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class IsCompleteBinaryTree {
    public static boolean isCompleteBinaryTree(Node<?> root) {
        if (root == null) return true;

        Queue<Node<?>> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean leafNull = false; // 没有页节点
        while (!queue.isEmpty()) {
            Node<?> node = queue.poll();
            Node<?> l = node.left;
            Node<?> r = node.right;

            if ((leafNull && (l != null || r != null)) || (l == null && r != null)) return false;

            if (l != null) queue.offer(l);
            if (r != null) queue.offer(r);

            if (l == null && r == null) leafNull = true;
        }
        return true;
    }

    public static boolean isComplete(Node<?> root) {
        return process(root).isCBT;
    }

    public static Info process(Node<?> node) {
        if (node == null) return new Info(true, true, 0);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int height = Math.max(lInfo.height, rInfo.height) + 1;

        boolean isFull = lInfo.isFull && rInfo.isFull && lInfo.height == rInfo.height;
        boolean isCBT = isFull;

        if (lInfo.isCBT && rInfo.isCBT) {
            if ((lInfo.isFull && lInfo.height == rInfo.height)
                    || (rInfo.isFull && lInfo.height == rInfo.height + 1)
                    || (lInfo.isFull && rInfo.isFull && lInfo.height == rInfo.height + 1)) {
                isCBT = true;
            }
        }

        return new Info(isFull, isCBT, height);
    }

    static class Info {
        boolean isFull;
        boolean isCBT;
        int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    /*public static boolean isComplete(Node<?> root) {
        return process(root).isCBT;
    }

    public static Info process(Node<?> node) {
        if (node == null) return new Info(true, true, 0);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int height = Math.max(lInfo.height, rInfo.height) + 1;

        boolean isFull = lInfo.isFull && rInfo.isFull && lInfo.height == rInfo.height,
                isCBT = false;

        if (isFull) {
            isCBT = true;
        } else {
            if (lInfo.isCBT && rInfo.isCBT) {
                if ((lInfo.isFull && lInfo.height == rInfo.height)
                        || (rInfo.isFull && lInfo.height == rInfo.height + 1)
                        || (lInfo.isFull && rInfo.isFull && lInfo.height == rInfo.height + 1)) {
                    isCBT = true;
                }
            }
        }

        return new Info(isFull, isCBT, height);
    }

    static class Info {
        boolean isFull; // 是否是满二叉树 没有子节点
        boolean isCBT;
        int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }*/
}