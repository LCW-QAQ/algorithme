class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    def partition(self, head: ListNode, x: int) -> ListNode:
        """分隔链表"""
        return self.partition_with_pointer(head, x)

    def partition_with_pointer(self, head, x):
        lt_head, lt_tail, ge_head, ge_tail = [None] * 4
        while head:
            next = head.next
            head.next = None
            if head.val < x:
                if not lt_head:
                    lt_head = lt_tail = head
                else:
                    lt_tail.next = head
                    lt_tail = lt_tail.next
            else:
                if not ge_head:
                    ge_head = ge_tail = head
                else:
                    ge_tail.next = head
                    ge_tail = ge_tail.next
            head = next

        # 小于区有值
        if lt_tail:
            lt_tail.next = ge_head
        # 那边有值返回那边
        return lt_head or ge_head

    def partition_dummy_head(self, head, x):
        lt_node, ge_node = ListNode(-1), ListNode(-1)
        lt_head, ge_head = lt_node, ge_node
        while head:
            if head.val < x:
                lt_node.next = head
                lt_node = lt_node.next
            else:
                ge_node.next = head
                ge_node = ge_node.next
            head = head.next
        ge_node.next = None
        lt_node.next = ge_head.next
        return lt_head.next
