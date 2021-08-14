package linked_list;

public class LinkedMid {

    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 获取长度是基数具有唯一中点链表的中点
     *
     * @param head 链表头节点
     */
    public static Node getMidInOdd(Node head) {
        if (head == null) return null;

        Node slow = head, fast = head;
        // 1 2 3 4 5
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 获取长度是偶数的链表的前中点
     *
     * @param head
     * @return
     */
    public static Node getPreMidInEven(Node head) {
        return getMidInOdd(head);
    }

    /**
     * 获取长度是偶数的链表的后中点
     * @param head
     * @return
     */
    public static Node getAfterMidInEven(Node head) {
        return getPreMidInEven(head).next;
    }

    public static void main(String[] args) {
        Node head = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        head.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        System.out.println("getPreMidInEven: " + getPreMidInEven(head).value);
        System.out.println("getAfterMidInEven: " + getAfterMidInEven(head).value);
        n5.next = new Node(6);
        System.out.println("getMidInOdd: " + getMidInOdd(head).value);
    }
}