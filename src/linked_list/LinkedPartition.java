package linked_list;

public class LinkedPartition {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * https://leetcode-cn.com/problems/partition-list/submissions/
     * @param head 链表头节点
     * @param x 基准数
     * @return 新的头节点
     */
    public ListNode partition(ListNode head, int x) {
        ListNode ltH = null, ltT = null, geH = null, geT = null;
        ListNode node = head;
        while (node != null) {
            ListNode next = node.next;
            node.next = null;

            if (node.val < x) {
                if (ltH == null) {
                    ltH = ltT = node;
                } else {
                    ltT.next = node;
                    ltT = node;
                }
            } else {
                if (geH == null) {
                    geH = geT = node;
                } else {
                    geT.next = node;
                    geT = node;
                }
            }
            node = next;
        }
        if (ltT != null) {
            ltT.next = geH;
        }
        return ltH != null ? ltH : geH;
    }
}
