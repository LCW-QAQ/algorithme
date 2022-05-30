"""
二叉树搜索树
"""
from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class Node:
    val: Any = None
    left: Optional["Node"] = None
    right: Optional["Node"] = None
    parent: Optional["Node"] = None


class BinarySearchTree:
    def __init__(self) -> None:
        self.root = None

    def add(self, val):
        self.root = self._add_by_recursion(self.root, Node(val))

    def find(self, val):
        return self._search_by_recursion(self.root, val)

    def remove(self, val):
        self.root = self._remove_by_recursion(self.root, val)

    def _remove_by_recursion(self, node: Optional[Node], val):
        if node is None:
            return node
        if node.val < val:
            node.right = self._remove_by_recursion(node.right, val)
        elif node.val > val:
            node.left = self._remove_by_recursion(node.left, val)
        elif node.left is not None and node.right is not None:
            node.val = self._find_min(node.right).val
            node.right = self._remove_by_recursion(node.right, val)
        else:
            node = node.left if node.left is not None else node.right
        return node

    def _find_min(self, node: Optional[Node]) -> Node:
        """指定子树的最小节点"""
        if node:
            while node.left:
                node = node.left
        return node

    def _traverse(self):
        def process(node):
            if node is None:
                return
            process(node.left)
            print(node.val, end=" ")
            process(node.right)
        process(self.root)
        print()

    def _add_by_recursion(self, node: Node, x: Node) -> Node:
        if node is None:
            node = x
        elif node.val < x.val:
            node.right = self._add_by_recursion(node.right, x)
        elif node.val > x.val:
            node.left = self._add_by_recursion(node.left, x)
        return node

    def _add_by_iteration(self, node: Node, x: Node) -> Node:
        if node is None:
            node = x
        parent = None
        while node is not None:
            parent = node
            if node.val < x.val:
                node = node.right
            else:
                node = node.left
        if parent.val < x.val:
            parent.right = x
        else:
            parent.left = x
        return node

    def _search_by_recursion(self, node: Optional[Node], val):
        if node is None:
            return node
        if node.val < val:
            return self._search_by_recursion(node.right, val)
        elif node.val > val:
            return self._search_by_recursion(node.left, val)
        else:
            return node

    def _search_by_iteration(self, node: Optional[Node], val):
        if node is None:
            return node
        while node is not None:
            if node.val < val:
                node = node.right
            elif node.val > val:
                node = node.left
            else:
                return node
        return node

    def __contains__(self, val):
        return self._search_by_recursion(self.root, val) is not None


if __name__ == "__main__":
    bst = BinarySearchTree()
    for i in range(5):
        bst.add(i)
    for i in range(9, -1, -1):
        bst.add(i)
    print(f"bst contains 3: {3 in bst}")
    print(f"bst contains 3: {31 in bst}")
    print(f"bst find 7: {bst.find(7).val}")
    bst.remove(4)
    bst.remove(8)
    bst.add(-1)
    bst._traverse()
