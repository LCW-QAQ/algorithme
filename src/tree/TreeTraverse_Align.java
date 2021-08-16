package tree;

import java.util.Stack;

public class TreeTraverse_Align {

    // 使用栈, 先序遍历二叉树
    public static void preTraverseByStack(Node<?> root) {
        if (root == null) return;

        Stack<Node<?>> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node<?> node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
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

            if (node.left != null) stack1.push(node.left);

            if (node.right != null) stack1.push(node.right);

        }
        while (!stack2.empty()) {
            System.out.println(stack2.pop().value);
        }
    }

    public static void postTraverseByStack2(Node<?> root) {
        if (root == null) return;

        Stack<Node<?>> stack = new Stack<>();
        stack.push(root);
        // 标记我的子节点是否被遍历过
        Node<?> flag = null;
        while (!stack.empty()) {
            Node<?> node = stack.peek();
            if (node.left != null && flag != node.left && flag != node.right) {
                stack.push(node.left);
            } else if (node.right != null && flag != node.right) {
                stack.push(node.right);
            } else {
                System.out.println(node.value);
                flag = stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        Node<Integer> node0 = new Node<>(0);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        node0.left = node1;
        node0.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        postTraverseByStack2(node0);

    }
}