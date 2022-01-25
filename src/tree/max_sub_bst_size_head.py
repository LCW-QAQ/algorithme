from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


@dataclass
class Info:
    max_sub_bst_head: Optional[Node]
    max_sub_bst_count: int
    count: int
    max_val: int = float("-inf")
    min_val: int = float("inf")


def process(node):
    if not node:
        return Info(None, 0, 0)
    linfo = process(node.left)
    rinfo = process(node.right)

    count = linfo.count + rinfo.count + 1
    max_val = max(node.val, linfo.max_val, rinfo.max_val)
    min_val = min(node.val, linfo.min_val, rinfo.min_val)
    left_is_bst = linfo.max_sub_bst_count == linfo.count
    right_is_bst = rinfo.max_sub_bst_count == rinfo.count
    node_max_sub_bst_count = float("-inf")
    # 以当前节点为头的整个子树, 是二叉搜索树
    if left_is_bst and right_is_bst and linfo.max_val < node.val < rinfo.min_val:
        node_max_sub_bst_count = count
    max_sub_bst_count = max(linfo.max_sub_bst_count, rinfo.max_sub_bst_count, node_max_sub_bst_count)

    max_sub_bst_head = None
    if max_sub_bst_count == linfo.max_sub_bst_count:
        max_sub_bst_head = linfo.max_sub_bst_head
    elif max_sub_bst_count == rinfo.max_sub_bst_count:
        max_sub_bst_head = rinfo.max_sub_bst_head
    elif max_sub_bst_count == count:
        max_sub_bst_head = node

    return Info(max_sub_bst_head, max_sub_bst_count, count, max_val, min_val)


def max_sub_bst_size(root: Node) -> Optional[Node]:
    """求二叉树树所有子树中, 最大的二叉搜索子树的头节点
    state info:
        x max_sub_bst_head
        max_sub_bst_count
        count
        max_val
        min_val
        1. 以x为头的二叉搜索子树
        2. x.left or x.right max_sub_bst_head
    """
    return process(root).max_sub_bst_head
