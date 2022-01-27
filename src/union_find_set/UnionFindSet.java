package union_find_set;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-27
 */
public class UnionFindSet<T> {
    static class Node<T> {
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

    // 记录值对应的节点
    private HashMap<T, Node<T>> nodes;

    // 记录父节点映射
    private HashMap<Node<T>, Node<T>> parentMap;

    // 记录头节点大小
    private HashMap<Node<T>, Integer> sizeMap;

    public UnionFindSet(Collection<T> collection) {
        nodes = new HashMap<>();
        parentMap = new HashMap<>();
        sizeMap = new HashMap<>();

        for (T value : collection) {
            final Node<T> node = new Node<>(value);
            nodes.put(value, node);
            parentMap.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    /**
     * 返回给定节点所在集合的头节点
     * <p>
     * 在找到头节点后, 会将node-head的链条扁平化
     */
    public Node<T> findHead(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        // 寻找node的头节点
        while (node != parentMap.get(node)) {
            stack.push(node);
            node = parentMap.get(node);
        }
        // 扁平化node-head的链条
        while (!stack.empty()) {
            parentMap.put(stack.pop(), node);
        }
        return node;
    }

    /**
     * 判断两个值是否在同一个集合中
     */
    public boolean isSameSet(T a, T b) {
        return findHead(nodes.get(a)) == findHead(nodes.get(b));
    }

    /**
     * 合并a, b所在的集合
     */
    public void union(T a, T b) {
        final Node<T> aHead = findHead(nodes.get(a));
        final Node<T> bHead = findHead(nodes.get(b));

        // a, b不在同一个集合中才需要合并
        if (aHead != bHead) {
            final Integer aSize = sizeMap.get(aHead);
            final Integer bSize = sizeMap.get(bHead);

            Node<T> largestHead = aSize > bSize ? aHead : bHead;
            Node<T> smallerHead = largestHead == aHead ? bHead : aHead;

            // 将更小的集合连接到更大的集合头, 避免出现长链表
            parentMap.put(smallerHead, largestHead);
            sizeMap.put(largestHead, aSize + bSize);
            // smallerHead 不在是头节点, 删除器size信息
            sizeMap.remove(smallerHead);
        }
    }

    /**
     * 返回并查集中有多少个集合
     */
    public int size() {
        return sizeMap.size();
    }
}