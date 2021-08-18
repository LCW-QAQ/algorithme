package tree;

import java.util.Stack;

// https://leetcode-cn.com/problems/balanced-binary-tree/
public class IsBalancedBinaryTree {

    // 判断是否平衡二叉树
    public static boolean isBalancedBinaryTree(Node<?> root) {
        return process(root).isBalanced;
    }

    public static boolean isBalanced(Node<?> root) {
        return height(root) >= 0;
    }

    private static int height(Node<?> node) {
        if (node == null) return 0;

        int lHeight = height(node.left);
        int rHeight = height(node.right);

        if (lHeight == -1 || rHeight == -1 || Math.abs(lHeight - rHeight) > 1) {
            return -1;
        }

        return Math.max(lHeight, rHeight) + 1;
    }

    static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static Info process(Node<?> node) {
        if (node == null) new Info(true, 0);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int height = Math.max(lInfo.height, rInfo.height) + 1;
        boolean isBalanced = true;

        if (!lInfo.isBalanced || !rInfo.isBalanced) {
            isBalanced = false;
        } else if (Math.abs(lInfo.height - rInfo.height) > 1) {
            isBalanced = false;
        }

        return new Info(isBalanced, height);
    }
}
