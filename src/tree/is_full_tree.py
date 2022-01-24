from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


@dataclass()
class Info:
    height: int
    count: int


def process(node):
    if not node:
        return Info(0, 0)
    linfo = process(node.left)
    rinfo = process(node.right)

    count = linfo.count + rinfo.count + 1
    height = max(linfo.height, rinfo.height) + 1

    return Info(height, count)


def is_full_tree(root: Node):
    """判断是否是满二叉树
    满二叉树节点数为2倍高度-1
    state info:
        x is full
        x.left is full and x.right is full
        height
        count
    """
    info = process(root)
    return info.height * 2 - 1 == info.count
