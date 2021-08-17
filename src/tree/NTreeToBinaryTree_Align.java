package tree;

import java.util.ArrayList;
import java.util.List;

// https://leetcode-cn.com/problems/encode-n-ary-tree-to-binary-tree/
public class NTreeToBinaryTree_Align {
    static class Node {
        int val;
        List<Node> children;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    class Codec {
        public TreeNode encode(Node root) {
            if (root == null) return null;
            TreeNode tNode = new TreeNode(root.val);
            tNode.left = encoding(root.children);
            return tNode;
        }

        public TreeNode encoding(List<Node> children) {
            if (children == null || children.size() == 0) return null;

            TreeNode root = null, preTNode = null;
            for (Node child : children) {
                TreeNode tNode = new TreeNode(child.val);
                if (root == null) {
                    root = tNode;
                } else {
                    preTNode.right = tNode;
                }
                preTNode = tNode;
                tNode.left = encoding(child.children);
            }

            return root;
        }


        public Node decode(TreeNode root) {
            if (root == null) return null;

            Node node = new Node(root.val);
            node.children = decoding(root.left);
            return node;
        }

        private List<Node> decoding(TreeNode root) {
            if (root == null) return null;

            List<Node> children = new ArrayList<>();
            TreeNode tNode = root;
            while (tNode != null) {
                Node node = new Node(tNode.val);
                node.children = decoding(tNode.left);
                children.add(node);
                tNode = tNode.right;
            }
            return children;
        }
    }
}