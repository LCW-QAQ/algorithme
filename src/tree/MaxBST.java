package tree;

// 求树形结构所有子树中, 最大的二叉搜索树的节点数量
public class MaxBST {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxBST(Node root) {
        return process(root).maxBSTSubSize;
    }

    public static Info process(Node node) {
        if (node == null) return new Info(0, 0, Long.MAX_VALUE, Long.MIN_VALUE);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int maxBSTSubSize = 0;
        int size = lInfo.size + rInfo.size + 1;
        long min = Math.min(node.value, Math.min(lInfo.min, rInfo.min));
        long max = Math.max(node.value, Math.max(lInfo.max, rInfo.max));

        boolean leftBST = lInfo.maxBSTSubSize == lInfo.size;
        boolean rightBST = rInfo.maxBSTSubSize == rInfo.size;

        if (leftBST && rightBST && lInfo.max < node.value && rInfo.min > node.value) {
            maxBSTSubSize = lInfo.size + rInfo.size + 1;
        }

        return new Info(Math.max(lInfo.maxBSTSubSize, Math.max(rInfo.maxBSTSubSize, maxBSTSubSize)), size, min, max);
    }

    static class Info {
        // 最大二叉搜索子树的Size
        int maxBSTSubSize;
        // 当前树的大小
        int size;
        // 当前树中最小值
        long min;
        // 当前树中最大值
        long max;

        public Info(int maxBSTSubSize, int size, long min, long max) {
            this.maxBSTSubSize = maxBSTSubSize;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }
}