package linked_list;

// https://leetcode-cn.com/problems/linked-list-cycle/
public class LinkedCycle {
    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static boolean hasCycle(Node head) {
        if (head == null || head.next == null || head.next.next == null) return false;

        Node slow = head.next, fast = head.next.next;

        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) return false;
            slow = slow.next;
            fast = fast.next.next;
        }

        return true;
    }

    public static Node detectCycle(Node head) {
        if (head == null || head.next == null || head.next.next == null) return null;

        Node slow = head.next, fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

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

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n1;
        System.out.println(hasCycle(n1));
        System.out.println(detectCycle(n1));
        System.out.println(detectCycleDoWhile(n1));
    }
}
