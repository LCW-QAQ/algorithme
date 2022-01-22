from dataclasses import dataclass
from typing import Optional, Any


@dataclass
class TreeNode:
    val: Any
    left: Optional["TreeNode"] = None
    right: Optional["TreeNode"] = None


def pre(node):
    if not node:
        return
    print(node.val, end=" ")
    pre(node.left)
    pre(node.right)


def mid(node):
    if not node:
        return
    mid(node.left)
    print(node.val, end=" ")
    mid(node.right)


def post(node):
    if not node:
        return
    post(node.left)
    post(node.right)
    print(node.val, end=" ")


def pre_by_stack(root):
    stack = [root]
    while stack:
        node = stack.pop()
        print(node.val, end=" ")
        if node.right:
            stack.append(node.right)
        if node.left:
            stack.append(node.left)


def mid_by_stack(root):
    stack = []
    node = root
    while stack or node:
        if node:
            stack.append(node)
            node = node.left
        else:
            node = stack.pop()
            print(node.val, end=" ")
            node = node.right


def post_by_stack(root):
    stack1 = [root]
    stack2 = []
    while stack1:
        node = stack1.pop()
        stack2.append(node)
        if node.left:
            stack1.append(node.left)
        if node.right:
            stack1.append(node.right)
    while stack2:
        print(stack2.pop().val, end=" ")


def post_by_stack2(root):
    stack = [root]
    # 用于记录是子节点否被遍历过
    flag = None
    while stack:
        node = stack[-1]
        if node.left and flag is not node.left and flag is not node.right:
            stack.append(node.left)
        elif node.right and flag is not node.right:
            stack.append(node.right)
        else:
            print(node.val, end=" ")
            flag = stack.pop()


if __name__ == '__main__':
    node = TreeNode(0, TreeNode(1, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(5), TreeNode(6)))
    pre(node)
    print()
    mid(node)
    print()
    post(node)
    print()
    print()
    pre_by_stack(node)
    print()
    mid_by_stack(node)
    print()
    post_by_stack(node)
    print()
    post_by_stack2(node)
    print()
