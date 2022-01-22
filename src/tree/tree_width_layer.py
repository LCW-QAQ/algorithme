from collections import deque
from dataclasses import dataclass
from typing import Optional, Dict


@dataclass
class Node:
    val: int
    left: Optional["Node"] = None
    right: Optional["Node"] = None


def tree_width_layer(root: Node) -> int:
    """返回给定二叉树最宽的层有多少个节点"""
    q = deque()
    max_width = 0
    # 当前层的宽度
    cur_width = 0
    cur_end, next_end = None, None
    q.append(root)
    while q:
        node = q.popleft()
        if node.left is not None:
            q.append(node.left)
            next_end = node.left
        if node.right is not None:
            q.append(node.right)
            next_end = node.right
        cur_width += 1
        # 如果当前层要结束了, 就更新max_width
        if node is cur_end:
            max_width = max(max_width, cur_width)
            cur_width = 0
            # 将cur_end设为下一层的结尾
            cur_end = next_end
    return max_width


def tree_width_layer_by_map(root: Node) -> int:
    q = deque()
    q.append(root)
    # 当前在第几层
    cur_level = 1
    # 当前层的宽度
    cur_width = 0
    # 最大宽度
    max_width = 0
    # key当前节点, value当前节点在第几层
    level_map: Dict[Node, int] = {}
    while q:
        node = q.popleft()
        cur_node_level = level_map[node]
        if node.left is not None:
            q.append(node.left)
        if node.right is not None:
            q.append(node.right)
        if cur_node_level == cur_level:
            cur_width += 1
        else:
            max_width = max(max_width, cur_width)
            cur_level += 1
            cur_width = 0
    return max_width
