package linked_list;

// 给定两个链表的头节点, 如果两个链表相交返回相交的第一个节点, 否则返回null
public class LinkedIntersect {
    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    // 获取链表的入环节点
    public static Node detectCycleDoWhile(Node head) {
        if (head == null) return null;

        Node slow = head, fast = head;
        do {
            if (fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        while (slow != fast);
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    // 两个链表不相交的情况
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node node1 = head1, node2 = head2;
        while (node1 != node2) {
            node1 = node1 != null ? node1.next : head2;
            node2 = node2 != null ? node2.next : head1;
        }
        return node1;
    }

    // 两个链表都有环, 如果其中一个链表有环另一个无环不可能相交, 这是单链表
    public static Node bothLoop(Node head1, Node head2, Node loop1, Node loop2) {
        // 同一入环点
        if (loop1 == loop2) {
            Node node1 = head1, node2 = head2;
            while (node1 != node2) {
                node1 = node1 != loop1 ? node1.next : head2;
                node2 = node2 != loop1 ? node2.next : head1;
            }
            return node1;
        } else { // 不同入环点
            Node node = loop1.next;
            while (node != loop1) {
                if (node == loop2) return loop1;
                node = node.next;
            }
            return null;
        }
    }
}
