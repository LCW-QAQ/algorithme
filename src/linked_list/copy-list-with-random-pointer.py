from typing import Optional


class Node:
    def __init__(self, x: int, next: 'Node' = None, random: 'Node' = None):
        self.val = int(x)
        self.next = next
        self.random = random


class Solution:
    def copyRandomList(self, head: 'Optional[Node]') -> 'Optional[Node]':
        return self.copyRandomList_by_pointer(head)

    def copyRandomList_by_dict(self, head):
        if not head:
            return None
        m = {}
        node = head
        while node:
            m[node] = Node(node.val)
            node = node.next
        node = head
        while node:
            # 注意python不允许None key
            if node.next:
                m[node].next = m[node.next]
            if node.random:
                m[node].random = m[node.random]
            node = node.next
        return m[head]

    def copyRandomList_by_pointer(self, head):
        if not head:
            return None
        node = head
        while node:
            # 下一个元素
            next = node.next
            # 拷贝当前元素, 放到下一个位置
            node.next = Node(node.val)
            node.next.next = next
            # 来到真正的下一个位置
            node = next

        node = head
        # 连接random
        while node:
            # 记录真正的下一个元素
            next = node.next.next
            copy_node = node.next
            copy_node.random = node.random.next if node.random else None
            node = next

        # 拆分新旧链表
        node = head
        ans = node.next
        while node:
            next = node.next.next
            copy_node = node.next
            node.next = next
            copy_node.next = next.next if next else None
            node = next
        return ans
