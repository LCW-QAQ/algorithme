package tree;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
public class TreeSerialized {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return serializeByBFS(root);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return deserializeByBFS(data);
    }

    public static String serializeByBFS(TreeNode root) {
        StringBuilder sbl = new StringBuilder();
        if (root == null) {
            sbl.append("None,");
        } else {
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    sbl.append(String.valueOf(node.val).concat(","));
                } else {
                    sbl.append("None,");
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    sbl.append(String.valueOf(node.val).concat(","));
                } else {
                    sbl.append("None,");
                }
            }
        }
        return sbl.toString();
    }

    public static TreeNode deserializeByBFS(String data) {
        if ("None,".equals(data)) return null;

        Queue<String> strQueue = Arrays.stream(data.split(","))
                .collect(Collectors.toCollection(ArrayDeque::new));
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        TreeNode root = genNode(strQueue.poll());
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            node.left = genNode(strQueue.poll());
            node.right = genNode(strQueue.poll());
            if (node.left != null) nodeQueue.offer(node.left);
            if (node.right != null) nodeQueue.offer(node.right);
        }
        return root;
    }

    public static TreeNode genNode(String str) {
        return "None".equals(str) ? null : new TreeNode(Integer.parseInt(str));
    }

    public static String serializeByDFS(TreeNode root) {
        StringBuilder sbl = new StringBuilder();
        presNode(root, sbl);
        return sbl.toString();
    }

    public static void presNode(TreeNode head, StringBuilder sbl) {
        if (head == null) {
            sbl.append("None,");
        } else {
            sbl.append(String.valueOf(head.val).concat(","));
            presNode(head.left, sbl);
            presNode(head.right, sbl);
        }
    }

    public static TreeNode deserializeByDFS(String data) {
        if ("None,".equals(data)) return null;

        ArrayDeque<String> charsDeque = Arrays.stream(data.split(","))
                .collect(Collectors.toCollection(ArrayDeque::new));
        return dePresNode(charsDeque);
    }

    public static TreeNode dePresNode(Queue<String> deque) {
        String value = deque.poll();
        if ("None".equals(value)) return null;
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = dePresNode(deque);
        node.right = dePresNode(deque);
        return node;
    }
}