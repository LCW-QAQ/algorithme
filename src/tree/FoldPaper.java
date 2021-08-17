package tree;

// 将纸条每次从下往上对折, 不要翻面, 求N次对折后, 纸条从上往下的折痕是什么(...凹..凸...)
public class FoldPaper {
    /*static class Node {
        boolean flag;
        Node left;
        Node right;

        public Node(boolean flag) {
            this.flag = flag;
        }
    }*/

    public static void printAllFolds(int n) {
        if (n < 1) return;
        process(1, n, true);
    }

    /**
     * 打印纸条对折n次后的样子
     *
     * @param i    当前是假想二叉树的第几层
     * @param n    一共有n层, 对折n次
     * @param flag 凹 = true, 凸 = false
     */
    private static void process(int i, int n, boolean flag) {
        if (i > n) return;

        process(i + 1, n, true);
        System.out.printf("%s ", flag ? "凹" : "凸");
        process(i + 1, n, false);
    }

    public static void main(String[] args) {
        printAllFolds(4);
    }
}
