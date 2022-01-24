from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


@dataclass()
class Info:
    max_sub_bst_count: int
    count: int
    max_val: int = float("-inf")
    min_val: int = float("inf")


def process(node):
    if not node:
        return Info(True, 0, 0)
    linfo = process(node.left)
    rinfo = process(node.right)

    count = linfo.count + rinfo.count + 1
    max_val = max(node.val, linfo.max_val, rinfo.max_val)
    min_val = min(node.val, linfo.min_val, rinfo.min_val)

    node_sub_bst_count = float("-inf")
    left_is_bst = linfo.max_sub_bst_count == linfo.count
    right_is_bst = rinfo.max_sub_bst_count == rinfo.count
    if left_is_bst and right_is_bst and linfo.max_val < node.val < rinfo.min_val:
        node_sub_bst_count = linfo.count + rinfo.count + 1

    max_sub_bst_count = max(linfo.max_sub_bst_count, rinfo.max_sub_bst_count, node_sub_bst_count)

    return Info(max_sub_bst_count, count, max_val, min_val)


def max_sub_bst_size(root: Node) -> int:
    """求二叉树树所有子树中, 最大的二叉搜索树的节点数量
    state info:
        x is bst
        x.left max val and x.right min val
        x.max_val
        x.min_val
        x.count
        x.max_sub_bst_count
    """
    return process(root).max_sub_bst_count
