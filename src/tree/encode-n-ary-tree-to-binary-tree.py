from dataclasses import dataclass
from typing import List, Optional


@dataclass
class Node:
    val: int
    children: List["Node"] = None


@dataclass
class TreeNode:
    val: int
    left: Optional["TreeNode"] = None
    right: Optional["TreeNode"] = None


class Codec:
    def encode(self, root: Node) -> TreeNode:
        """将多叉数转为二叉树"""
        head = TreeNode(root.val)
        head.left = self.en(root.children)
        return head

    def decode(self, root: TreeNode) -> Node:
        """将二叉树转为多叉树"""
        return Node(root.val, self.de(root.left))

    def en(self, children):
        """将多叉数的子节点转成二叉树节点"""
        head, node = None, None
        for child in children:
            tnode = TreeNode(child.val)
            if head is None:
                head = tnode
            else:
                node.right = tnode
            node = tnode
            node.left = self.en(child.children)
        return head

    def de(self, root: TreeNode):
        """根据二叉树节点, 转换成多叉树"""
        children = []
        while root is not None:
            node = Node(root.val, self.de(root.left))
            children.append(node)
            root = root.right
        return children
