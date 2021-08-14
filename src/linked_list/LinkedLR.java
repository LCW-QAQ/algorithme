package linked_list;

public class LinkedLR {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 将给定链表L1 -> L2 -> L3 -> L4 -> R1 -> R2 -> R3 -> r4 -> null 转换成 L1 -> R1 -> L2 -> R2 ...
     */
    public static void algorithm(Node head) {
        if (head == null) return;

        Node slow = head, fast = head;
        // 找中点
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node r = slow.next;
        Node pre = null;
        slow.next = null;
        while (r != null) {
            Node next = r.next;
            r.next = pre;
            pre = r;
            r = next;
        }

        Node l = head;
        r = pre;
        while (l != null && r != null) {
            Node lNext = l.next;
            Node rNext = r.next;

            l.next = r;
            l = lNext;

            r.next = l;
            r = rNext;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        Node n1 = new Node(2);
        Node n2 = new Node(3);
        head.next = n1;
        n1.next = n2;
        n2.next = new Node(4);

        algorithm(head);

        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }
}
