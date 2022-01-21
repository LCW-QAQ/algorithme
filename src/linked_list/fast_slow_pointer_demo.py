"""
快慢指针demo
"""
from dataclasses import dataclass
import unittest
from typing import Optional


@dataclass
class Node:
    value: int
    next_n: Optional["Node"] = None

    def __str__(self) -> str:
        return f"{{{self.value}}}"


# 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
def q1(head: Optional[Node]) -> Optional[Node]:
    # 链表是三个一下, 返回head
    if head is None or head.next_n is None or head.next_n.next_n is None:
        return head

    fast, slow = head.next_n.next_n, head.next_n
    # 当fast移动到不能再移动的时候, slow就来到了唯一中点或上中点
    while fast.next_n is not None and fast.next_n.next_n is not None:
        slow = slow.next_n
        fast = fast.next_n.next_n
    return slow


# 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
def q2(head):
    if head is None or head.next_n is None:
        return head

    fast, slow = head.next_n, head.next_n
    while fast.next_n is not None and fast.next_n.next_n is not None:
        slow = slow.next_n
        fast = fast.next_n.next_n
    return slow


class FastSlowPointerDemoTest(unittest.TestCase):
    def node_iter_print(self, node):
        while node is not None:
            print(node, end=" ")
            node = node.next_n
        # 记得换行, joker
        print()

    def gen_linklist(self, n):
        nodes = [Node(i) for i in range(n)]
        root = nodes[0]
        for i in range(len(nodes) - 1):
            nodes[i].next_n = nodes[i + 1]
        return root

    def test_q1_even(self):
        root = self.gen_linklist(10)
        self.node_iter_print(root)
        res = q1(root)
        print(res)
        self.assertTrue(res.value == 4)

    def test_q1_odd(self):
        root = self.gen_linklist(9)
        self.node_iter_print(root)
        res = q1(root)
        print(res)
        self.assertTrue(res.value == 4)

    def test_q2_even(self):
        root = self.gen_linklist(10)
        self.node_iter_print(root)
        res = q2(root)
        print(res)
        self.assertTrue(res.value == 5)

    def test_q2_odd(self):
        root = self.gen_linklist(9)
        self.node_iter_print(root)
        res = q2(root)
        print(res)
        self.assertTrue(res.value == 4)
