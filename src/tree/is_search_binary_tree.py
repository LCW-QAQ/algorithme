from dataclasses import dataclass
from typing import Any, Optional


# https://leetcode-cn.com/problems/validate-binary-search-tree/submissions/

@dataclass
class Node:
    val: Any
    left: Optional["Node"] = None
    right: Optional["Node"] = None


def process_lower_upper(node, lower=float("-inf"), upper=float("inf")):
    if not node:
        return True
    return lower < node.val < upper and process_lower_upper(node.left, lower, node.val) and process_lower_upper(
        node.right, node.val, upper)


@dataclass()
class Info:
    is_bst: bool
    max_val: int = float("-inf")
    min_val: int = float("inf")


def process(node):
    if not node:
        return Info(True)
    linfo = process(node.left)
    rinfo = process(node.right)

    # max_val与min_val 表示的是当前树的最大值与最小值, 因此当前树的值node.val也要加入比较汇总
    max_val = max(node.val, linfo.max_val, rinfo.max_val)
    min_val = min(node.val, linfo.min_val, rinfo.min_val)
    is_bst = linfo.is_bst and rinfo.is_bst and linfo.max_val < node.val < rinfo.min_val

    return Info(is_bst, max_val, min_val)


def is_search_binary_tree(node: Node) -> bool:
    """判断是否是搜索二叉树
    state info:
        x is bst
        x.left is bst and x.right is bst
        bst = x.left.val < x.right.val ??? wrong! 需要保证 x.left子树的最大值 < x.right的最小值
        bst = max(x.left) < min(x.right)

        is_bst
        lmax
        rmin
    """
    return process(node).is_bst
