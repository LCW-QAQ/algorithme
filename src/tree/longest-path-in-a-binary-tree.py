from dataclasses import dataclass


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


def max_distance_by_iter_map(root: TreeNode) -> int:
    if not root:
        return 0

    def get_pre_list(root):
        """获取先序遍历"""

        def fill_pre_list(node, pre_list):
            if not node:
                return
            pre_list.append(node)
            fill_pre_list(node.left, pre_list)
            fill_pre_list(node.right, pre_list)

        pre_list = []
        fill_pre_list(root, pre_list)
        return pre_list

    def get_parent_map(root):
        """获取每个节点的父节点字典"""

        def fill_parent_map(node, parent_map):
            if node.left:
                parent_map[node.left] = node
                fill_parent_map(node.left, parent_map)
            if node.right:
                parent_map[node.right] = node
                fill_parent_map(node.right, parent_map)

        parent_map = {root: None}
        fill_parent_map(root, parent_map)
        return parent_map

    def distance(parent_map, n1, n2):
        """返回n1, n2两个节点之间的距离"""
        n1_set = set()
        node = n1
        n1_set.add(node)
        # 获取n1一直向上直到头节点的链条
        while parent_map[node]:
            node = parent_map[node]
            n1_set.add(node)
        node = n2
        # n2一直向上直到第一次与n1_set链条相遇, 那么该节点就是n1,n2的最近公共祖先
        while node not in n1_set:
            node = parent_map[node]
        lowest_common_ancestor = node

        # 从n1,n2一直向上找知道遇到公共祖先
        node = n1
        distance1 = 1
        while node is not lowest_common_ancestor:
            node = parent_map[node]
            distance1 += 1
        node = n2
        distance2 = 1
        while node is not lowest_common_ancestor:
            node = parent_map[node]
            distance2 += 1
        # 此时 distance1 + distance2 - 1 就是n1, n2之间的距离
        return distance1 + distance2 - 1

    pre_list = get_pre_list(root)
    parent_map = get_parent_map(root)
    max_val = 0

    # 暴力枚举每两个几点之间的距离
    for i in range(len(pre_list)):
        for j in range(i, len(pre_list)):
            max_val = max(max_val, distance(parent_map, pre_list[i], pre_list[j]))
    return max_val


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


def longest_path(root: TreeNode) -> int:
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
