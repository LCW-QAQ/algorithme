package linked_list;

public class SingleLinkedList<T> {

    private Node<T> head;

    private Node<T> tail;

    public SingleLinkedList() {
    }

    public void pushBack(T t) {
        Node<T> newNode = new Node<>(t);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void reverseIter() {
        if (head == null) return;

        Node<T> pre = null;
        Node<T> node = head;
        Node<T> next = head.next;
        tail = head;

        while (true) {
            node.next = pre;

            if (next == null) break;

            pre = node;
            node = next;
            next = next.next;
        }
        head = node;
    }

    public void reverseIter2() {
        if (head == null) return;

        Node<T> node = head, pre = null;

        while (node != null) {
            Node<T> next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }

        head = pre;
    }

    public void reverseRecursion() {
        head = _reverse(head);
    }

    public Node<T> _reverse(Node<T> node) {
        if (node == null || node.next == null) {
            return node;
        } else {
            Node<T> newHead = _reverse(node.next);
            node.next.next = node;
            node.next = null;
            return newHead;
        }
    }

    public void iter() {
        Node<T> node = head;
        while (node != null) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        System.out.println();
    }

    static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + (next == null ? "null" : next.item) +
                    '}';
        }
    }

    public static void main(String[] args) {
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.pushBack(i);
        }
//        list.reverseIter();
//        list.reverseRecursion();
        list.reverseIter2();
//        list.pushBack(1010);
        list.iter();
    }
}
