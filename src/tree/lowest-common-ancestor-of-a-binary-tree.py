# https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
from dataclasses import dataclass
from typing import Optional


class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


@dataclass
class Info:
    a_exist: bool
    b_exist: bool
    ans: Optional["TreeNode"]


def process(node, a, b):
    if not node:
        return Info(False, False, None)
    linfo = process(node.left, a, b)
    rinfo = process(node.right, a, b)

    a_exist = node == a or linfo.a_exist or rinfo.a_exist
    b_exist = node == b or linfo.b_exist or rinfo.b_exist
    # 要么左右两边有ans, 要么当前树上有a, b ans is node
    ans = linfo.ans or rinfo.ans or (a_exist and b_exist and node)

    return Info(a_exist, b_exist, ans)


def dfs(node, a, b):
    """
    子树问题, 需要左右子都满足, 然后看当前树是否满足, 所以是dfs 后续遍历
    """
    if not node:
        return None

    # 如果找到了a or b就返回
    if node == a or node == b:
        return node

    # 没找到a or b就去左右子树上找
    l_have = dfs(node.left, a, b)
    r_have = dfs(node.right, a, b)

    # 如果左右子树上找到了a or b, 那么答案只可能是当前节点
    if l_have and r_have:
        return node
    else:
        # 左右子树总有一边有答案(题目保证数据有答案)
        return l_have or r_have


class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        """二叉树的最近公共祖先
        百度百科中最近公共祖先的定义为：
            对于有根树 T 的两个节点 p、q
            最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大(一个节点也可以是它自己的祖先)
        info:
            ans 与 x 无关:
                ans有可能在x.left or x.right出现过了
                这个树上没有a or b
            ans 与 x 有关:
                这个树上有a or b:
                    ab分别在x.left或x.right, ans is x
                    x is (a or b), ans is x
        """
        return dfs(root, p, q)
