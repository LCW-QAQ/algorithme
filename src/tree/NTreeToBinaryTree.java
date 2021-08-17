package tree;

import java.util.ArrayList;
import java.util.List;

// https://leetcode-cn.com/problems/encode-n-ary-tree-to-binary-tree/
public class NTreeToBinaryTree {
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

            TreeNode node = new TreeNode(root.val);
            node.left = encoding(root.children);
            return node;
        }

        /**
         * 将给定的子节点们转换成一颗二叉树返回
         *
         * @param children 子节点
         * @return 转换后二叉树的根节点
         */
        private TreeNode encoding(List<Node> children) {
            TreeNode root = null;
            TreeNode preNode = null;
            for (Node child : children) {
                // 先创建子节点的TreeNode
                TreeNode tNode = new TreeNode(child.val);
                // 如果是根节点记录一下, 后面要返回
                if (root == null) {
                    root = tNode;
                } else {
                    // 不是根节点, 前一个节点的右子树连指向新节点
                    preNode.right = tNode;
                }
                // 记录当前节点, 方便下一次访问这个节点
                preNode = tNode;
                // 先要将所有子节点转换成功后, 才能继续
                preNode.left = encoding(child.children);
            }
            return root;
        }

        public Node decode(TreeNode root) {
            if (root == null) return null;

            return new Node(root.val, decoding(root.left));
        }

        /**
         * 将给定二叉树转换成包含所有子节点的集合
         *
         * @param root 二叉树根节点, 传进来的回事二叉树的左子树, 左子树的右子树上就是所有children
         * @return 多叉数的所有子节点
         */
        private List<Node> decoding(TreeNode root) {
            if (root == null) return null;

            // 这个函数就是把当前二叉树节点的所有右子树找出来
            ArrayList<Node> children = new ArrayList<>();
            TreeNode node = root;
            while (node != null) {
                // 右子树的左子树的右子树上也可能有节点, 递归找到所有子节点后才完成(深度优先遍历)
                Node newNode = new Node(node.val, decoding(node.left));
                children.add(newNode);
                node = node.right;
            }
            return children;
        }
    }
}