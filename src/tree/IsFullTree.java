package tree;

// 判断是否是满二叉树
public class IsFullTree {
    public static boolean isFull(Node<?> root) {
        Info info = process(root);
        return info.count == info.height * 2 - 1;
    }

    public static Info process(Node<?> node) {
        if (node == null) return new Info(0, 0);

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int height = Math.max(lInfo.height, rInfo.height) + 1,
                count = lInfo.count + rInfo.count + 1;

        return new Info(height, count);
    }

    static class Info {
        int height;
        int count;

        public Info(int height, int count) {
            this.height = height;
            this.count = count;
        }
    }
}
