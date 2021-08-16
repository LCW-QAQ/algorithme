package tree;

import java.util.Stack;

public class TreeTraverse {
    /**
     * 二叉树递归序, 遍历
     *
     * @param root 根节点
     */
    public static void traverseRecursion(Node<?> root) {
        if (root == null) return;

        traverseRecursion(root.left);

        traverseRecursion(root.right);
    }

    // 使用栈, 先序遍历二叉树
    public static void preTraverseByStack(Node<?> root) {
        if (root == null) return;

        Stack<Node<?>> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node<?> node = stack.pop(); // 头节点
            System.out.println(node.value);
            // 先压右边再压左边, 这是栈, 会先弹出左边
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    // 使用栈, 中序遍历二叉树
    public static void midTraverseByStack(Node<?> root) {
        if (root == null) return;

        Stack<Node<?>> stack = new Stack<>();
        Node<?> node = root;
        while (!stack.empty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.println(node.value);
                node = node.right;
            }
        }
    }

    // 使用栈, 后序遍历二叉树
    public static void postTraverseByStack(Node<?> root) {
        if (root == null) return;

        Stack<Node<?>> stack1 = new Stack<>();
        Stack<Node<?>> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.empty()) {
            Node<?> node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        while (!stack2.empty()) {
            System.out.println(stack2.pop().value);
        }
    }
}