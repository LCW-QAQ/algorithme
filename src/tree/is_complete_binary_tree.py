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


def is_complete_binary_tree_by_treedp_template(root: Node) -> bool:
    """判断一个树是否是完全二叉树, 树形dp套路版
    state info:
        x is cbt

    """

    @dataclass
    class Info:
        is_full: bool
        is_cbt: bool
        height: int

    def process(node):
        if not node:
            return Info(True, True, 0)
        linfo = process(node.left)
        rinfo = process(node.right)

        is_full = linfo.is_full and rinfo.is_full and linfo.height == rinfo.height
        height = max(linfo.height, rinfo.height) + 1
        is_cbt = is_full or (linfo.is_cbt and rinfo.is_full and linfo.height == rinfo.height + 1) or (
                linfo.is_full and rinfo.is_full and linfo.height == rinfo.height + 1) or (
                         linfo.is_full and rinfo.is_cbt and linfo.height == rinfo.height)

        return Info(is_full, is_cbt, height)

    return process(root).is_cbt
