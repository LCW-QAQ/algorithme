package tree;

// 找到给定节点的后继后继节点, 后继节点就是中序遍历中x的下一个节点
public class TreeSuccessor {

    static class Node<T> {
        T value;
        Node<?> left;
        Node<?> right;
        Node<?> parent;
    }

    public static Node<?> getSuccessorNode(Node<?> node) {
        if (node == null) return null;

        if (node.parent != null && node == node.parent.left) { // 当前节点是父节点的左子树, 那父节点就是后继
            return node.parent;
        } else if (node.right != null) { // 右边不为null, 右数上最左节点就是后继
            Node<?> temp = node.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        } else { // 当前节点可能是某个节点的左子树中的最右的节点
            Node<?> parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }
}
