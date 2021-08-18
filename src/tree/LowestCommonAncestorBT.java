package tree;

// https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
public class LowestCommonAncestorBT {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return process(root, p, q).ans;
    }

    public static Info process(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return new Info(false, false, null);

        Info lInfo = process(node.left, p, q);
        Info rInfo = process(node.right, p, q);

        boolean findP = node == p || lInfo.findP || rInfo.findP;
        boolean findQ = node == q || lInfo.findQ || rInfo.findQ;
        TreeNode ans = null;

        if (lInfo.ans != null) {
            ans = lInfo.ans;
        } else if (rInfo.ans != null) {
            ans = rInfo.ans;
        } else if (findP && findQ) {
            ans = node;
        }

        return new Info(findP, findQ, ans);
    }

    static class Info {
        boolean findP;
        boolean findQ;
        TreeNode ans;

        public Info(boolean findP, boolean findQ, TreeNode ans) {
            this.findP = findP;
            this.findQ = findQ;
            this.ans = ans;
        }
    }
}