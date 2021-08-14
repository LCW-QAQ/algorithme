package linked_list;

import java.util.Stack;

public class LinkedPalindrome {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 判断链表是否回文, 使用容器
     *
     * @param head 链表头节点
     * @return 是否回文
     */
    public static boolean isPalindrome_UseContainer(Node head) {
        if (head == null) return true;

        Stack<Node> stack = new Stack<>();
        Node node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }
        node = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != node.value) return false;
            node = node.next;
        }
        return true;
    }

    public static boolean isPalindrome(Node head) {
        if (head == null) return true;

        Node slow = head, fast = head;

        // 找中点
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow 现在是中点
        Node r = slow.next; // 中点的下一个
        Node pre = slow;
        slow.next = null; // 中点next置null
        while (r != null) {
            Node next = r.next;
            r.next = pre;
            pre = r;
            r = next;
        }

        Node l = head;
        r = pre;
        Node last = pre; // 保存最后一个节点
        while (l != null && r != null) {
            if (l.value != r.value) return false;
            l = l.next;
            r = r.next;
        }

        r = last; // r重新指向最后一个节点
        pre = null;
        // 1 2 2 1
        // 把链表给调回去
        while (r != null) {
            Node next = r.next;
            r.next = pre;
            pre = r;
            r = next;
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        Node n1 = new Node(2);
        Node n2 = new Node(3);
        Node n3 = new Node(2);
        Node n4 = new Node(1);
        head.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        System.out.println(isPalindrome_UseContainer(head));
        System.out.println(isPalindrome(head));
    }
}