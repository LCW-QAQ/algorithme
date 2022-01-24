from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


@dataclass
class Info:
    is_balance: bool
    height: int


def is_balance_binary_tree(root: Node) -> bool:
    return process(root).is_balance


def process_height(node):
    if node is None:
        return 0
    lheight = process_height(node.left)
    rheight = process_height(node.right)

    # 返回-1表示不平衡
    if lheight == -1 or rheight == -1 or abs(lheight - rheight) > 1:
        return -1

    return max(lheight, rheight) + 1


def process(node):
    if node is None:
        return Info(True, 0)
    linfo = process(node.left)
    rinfo = process(node.right)

    is_balance = linfo.is_balance and rinfo.is_balance and abs(linfo.height - rinfo.height) <= 1
    height = max(linfo.height, rinfo.height) + 1

    return Info(is_balance, height)
