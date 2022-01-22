from collections import deque
from dataclasses import dataclass
from typing import Any, Optional


@dataclass
class TreeNode:
    val: Any
    left: Optional["TreeNode"] = None
    right: Optional["TreeNode"] = None


def tree_bfs(root):
    q = deque()
    q.append(root)
    while q:
        node = q.popleft()
        print(node.val, end=" ")
        if node.left:
            q.append(node.left)
        if node.right:
            q.append(node.right)


if __name__ == '__main__':
    node = TreeNode(0, TreeNode(1, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(5), TreeNode(6)))
    tree_bfs(node)
    print()
