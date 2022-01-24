from collections import deque
from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


def is_complete_binary_tree(root: Node) -> bool:
    """判断一个树是否是完全二叉树
    什么情况下不是完全二叉树?
        1. 左边没节点, 但是右边有
        2. 只要左右子节点其中一个没有, 那么接下来的节点还有子节点
    """
    q = deque()
    q.append(root)
    # 标记是否出现子节点不满的情况, 左右子节点其中一个出现过没有的情况(是否到达完全二叉树的结尾)
    flag = False
    while q:
        node = q.popleft()
        if (flag and (node.left or node.right)) or (not node.left and node.right):
            return False
        if node.left:
            q.append(node.left)
        if node.right:
            q.append(node.right)
        if not node.left or not node.right:
            flag = True
    return True
