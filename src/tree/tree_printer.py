from dataclasses import dataclass
from typing import Optional


@dataclass
class TreeNode:
    val: int
    left: Optional["TreeNode"] = None
    right: Optional["TreeNode"] = None


def tree_printer(node: Optional[TreeNode], height=0, width=16):
    if node is None:
        return
    tree_printer(node.right, height + 1, width)
    val = str(node.val)
    # 左边的宽度
    width_l = (width - len(val)) // 2
    width_r = width - len(val) - width_l
    print(f"{' ' * height * width}{' ' * width_l}{val}{' ' * width_r}")
    tree_printer(node.left, height + 1, width)


if __name__ == '__main__':
    node = TreeNode(0, TreeNode(1, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(5), TreeNode(6)))
    tree_printer(node)
