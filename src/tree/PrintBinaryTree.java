package tree;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrintBinaryTree {

    public static void printBTree(Node<?> root) {
        printBTreeFormat(root, 0, 16);
    }

    /**
     * 打印给定节点的所有子元素
     * @param root 根节点
     * @param height 高度
     * @param len 节点字符串的标准长度
     */
    private static void printBTreeFormat(Node<?> root, int height, int len) {
        if (root == null) return;

        printBTreeFormat(root.right, height + 1, len);
        String val = root.value.toString();

        int lenL = (len - val.length()) / 2;
        int lenR = len - val.length() - lenL;

        val = getSpace(lenL).concat(val).concat(getSpace(lenR));
        System.out.println(getSpace(height * len).concat(val));
        printBTreeFormat(root.left, height + 1, len);
    }

    /**
     * 生成给定长度的空格字符
     * @param num 字符数量
     * @return num长度的空格字符
     */
    private static String getSpace(int num) {
        return Stream.generate(() -> " ").limit(num).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(0);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node3.left = node7;

        printBTree(node);
    }
}
