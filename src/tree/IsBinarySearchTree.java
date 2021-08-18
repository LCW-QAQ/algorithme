package tree;

// https://leetcode-cn.com/problems/validate-binary-search-tree/submissions/
public class IsBinarySearchTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        return process(root).isBST;
    }

    public static Info process(TreeNode node) {
        if (node == null) return new Info(true, Long.MIN_VALUE, Long.MAX_VALUE);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        boolean isBST = true;
        long max = node.val, min = node.val;

        max = Math.max(max, Math.max(lInfo.max, rInfo.max));
        min = Math.min(min, Math.min(lInfo.min, rInfo.min));

        if (!lInfo.isBST || !rInfo.isBST ||
                lInfo.max >= node.val || rInfo.min <= node.val) {
            isBST = false;
        }

        return new Info(isBST, max, min);
    }

    static class Info {
        boolean isBST;
        long max;
        long min;

        public Info(boolean isBST, long max, long min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static void main(String[] args) {
        // [5,4,6,null,null,3,7]
        TreeNode node0 = new TreeNode(5);
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(6);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(7);
        node0.left = node1;
        node0.right = node2;
        node2.left = node3;
        node2.right = node4;

        System.out.println(isValidBST(node0));
    }
}
