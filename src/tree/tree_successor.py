from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class TreeNode:
    val: Any
    left: Optional["TreeNode"] = None
    right: Optional["TreeNode"] = None
    parent: Optional["TreeNode"] = None


def get_successor_node(node: TreeNode):
    """获取给定节点的后继节点, 后继节点指中序遍历中x的下一个节点"""
    # 是父节点的左子节点, 那么后继节点就是父节点
    if node.parent and node is node.parent.left:
        return node.parent
    # 有右子节点, 那么后继节点就在右子节点的最左边
    elif node.right:
        n1 = node.right
        while n1:
            n1 = n1.left
        return n1
    else:
        # 既不是父节点的左子节点, 也没有右节点, 只能是树中某个节点的最右边
        parent = node.parent
        while parent and parent.right is node:
            node = parent
            parent = parent.parent
        return parent
