from dataclasses import dataclass


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


@dataclass
class Info:
    max_distance: int
    height: int


def process(node):
    if not node:
        return Info(0, 0)
    linfo = process(node.left)
    rinfo = process(node.right)

    height = max(linfo.height, rinfo.height) + 1
    max_distance = max(linfo.max_distance, rinfo.max_distance, linfo.height + rinfo.height + 1)

    return Info(max_distance, height)


class Solution:
    def longestPath(self, root: TreeNode) -> int:
        """二叉树中的最长路径
        二叉树中任意节点x, 到其他节点y的最优路径, 经过的节点个数表示为x到y的路径
        求二叉树中的最长路径

        state info:
            x max distance
            x.left max distance
            x.right max distance
            x的最左边 and x的最右边 -> x.left.height and x.right.height
        """
        return process(root).max_distance
