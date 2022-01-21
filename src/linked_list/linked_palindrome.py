import unittest
from dataclasses import dataclass
from typing import Optional


@dataclass
class Node:
    value: int
    next_n: Optional["Node"] = None

    def __str__(self):
        return f"{{{self.value}}}"


def is_palindrome_by_container(head):
    """判断链表是否回文, 使用容器"""
    stack = []
    node = head
    while node is not None:
        stack.append(node)
        node = node.next_n
    node = head
    while stack:
        if stack.pop().value != node.value:
            return False
        node = node.next_n
    return True


def is_palindrome_by_pointer(head):
    """判断链表是否回文, 使用快慢指针"""
    if head is None:
        return True
    fast, slow = head, head
    # 找中点, 退出while时slow就是中点(唯一中点或上中点)
    while fast.next_n is not None and fast.next_n.next_n is not None:
        slow = slow.next_n
        fast = fast.next_n.next_n

    # 中点的下一个
    r = slow.next_n
    # 将中点的下一个置空
    slow.next_n = None
    pre = slow  # 用于记录, 右边链表反转后的头节点
    # 反转中点右边的链表
    while r is not None:
        next_n = r.next_n
        r.next_n = pre
        pre = r
        r = next_n

    # l, r分别是左右边两个链表的头节点
    l, r = head, pre
    while l and r:
        if l.value != r.value:
            return False
        l = l.next_n
        r = r.next_n

    # 将右边的链表调回去
    pre = None
    while pre:
        next_n = pre.next_n
        pre.next_n = pre
        pre = pre
        pre = next_n

    return True


if __name__ == '__main__':
    n1 = Node(0, Node(1, Node(1, Node(0))))
    n2 = Node(0, Node(1, Node(1, Node(1))))
    assert is_palindrome_by_container(n1)
    assert is_palindrome_by_pointer(n1)
    assert not is_palindrome_by_container(n2)
    assert not is_palindrome_by_pointer(n2)
