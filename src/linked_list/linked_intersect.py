"""
给定两个单链表头节点, head1, head2, 连个单链表可能有环
若连个链表相交, 返回第一个交点, 若交点有多个返回任意交点, 若没有交点返回None
"""
from dataclasses import dataclass
from typing import Optional


@dataclass
class Node:
    val: int
    next: Optional["Node"] = None


def two_linked_get_intersect_node(head1: Optional[Node], head2: Optional[Node]) -> Optional[Node]:
    # 任意头节点为None, 没有交点
    if not head1 or not head2:
        return None
    # 获取里那个链表的入环节点
    loop1 = get_intersect_node(head1)
    loop2 = get_intersect_node(head1)
    """
    1. 两个链表都无环 可能相交
    2. 一个有环, 一个无环 不可能相交
    3. 两个都有环 可能相交
        1. 入环节点一致
        2. 入环节点不一致 
    """
    if not loop1 and not loop2:
        return no_loop(head1, head2)
    elif loop1 and loop2:
        return both_loop(head1, head2, loop1, loop2)
    return None


def both_loop(head1, head2, loop1, loop2):
    """两个有环链表, 是否相交"""
    # 入环节点一致, 交点只可能在入环节点或入间节点前面
    if loop1 is loop2:
        node1, node2 = head1, head2
        n = 0
        while node1 is not loop1:
            node1 = node1.next
            n += 1
        while node2 is not loop2:
            node2 = node2.next
            n -= 1
        longer_node = head1 if n > 0 else head2
        shorter_node = head1 if head1 is not longer_node else head2
        n = abs(n)
        while n > 0:
            longer_node = longer_node.next
            n -= 1
        while longer_node is not shorter_node:
            longer_node = longer_node.next
            shorter_node = shorter_node.next
        return longer_node
    # 入环节点不一致, 遍历入环节点, 看能不能找到另一个入环节点
    else:
        node = loop1
        while node.next is not loop1:
            if node is loop2:
                return loop2
            node = node.next
        return None


def no_loop(head1, head2):
    """两个无环链表, 是否相交"""
    node1, node2 = head1, head2
    n = 0
    while node1.next:
        node1 = node1.next
        n += 1
    while node2.next:
        node2 = node2.next
        n -= 1
    # 最有一个节点不一样, 说明不相交
    if node1 is not node2:
        return None
    # 更长的链表头节点
    longer_node = head1 if n > 0 else head2
    # 更短的链表头节点
    shorter_node = head1 if head1 is not longer_node else head2
    # 长链表走差值步
    n = abs(n)
    while n > 0:
        longer_node = longer_node.next
        n -= 1
    while longer_node is not shorter_node:
        longer_node = longer_node.next
        shorter_node = shorter_node.next
    return longer_node


def get_intersect_node(head: Optional[Node]) -> Optional[Node]:
    """
    给你单链表的头节点, 该单链表可能有环
    若有环返回入环节点, 无环返回None
    """
    fast, slow = head, head
    while fast.next and fast.next.next:
        # 两个指针相遇说明有环
        if fast is slow:
            break
        slow = slow.next
        fast = fast.next.next
    else:
        return None
    fast = head
    while fast is not slow:
        fast = fast.next
        slow = slow.next
    return fast
