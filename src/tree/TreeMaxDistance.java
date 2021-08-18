package tree;

// 求二叉树中节点之间最大距离
public class TreeMaxDistance {
    public static int maxDistance(Node<?> root) {
        return process(root).maxDistance;
    }

    public static Info process(Node<?> node) {
        if (node == null) return new Info(0, 0);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int maxDistance = Math.max(lInfo.height + rInfo.height + 1, Math.max(lInfo.maxDistance, rInfo.maxDistance)),
                height = Math.max(lInfo.height, rInfo.height) + 1;

        return new Info(maxDistance, height);
    }

    static class Info {
        int maxDistance;
        int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }
}
