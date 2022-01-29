# https://leetcode-cn.com/problems/number-of-islands-ii/
"""
岛屿数量II
有一个矩阵
矩阵中可以有0, 1
上下左右相邻的1是一片岛屿
一开始矩阵中都是0
输入positions二维数组, positions[i] 表示i时刻矩阵的(position[i][0], position[i][1])变为1
求每个时刻矩阵中的岛屿数量
"""
from typing import List, Any, Iterable, Dict


class Solution:
    def numIslandsII(self, m: int, n: int, positions: List[List[int]]) -> List[int]:
        return self.numIslandsII_ufs(m, n, positions)

    def numIslandsII_ufs(self, m, n, positions):
        class UnionFindSet:
            class Node:
                def __init__(self, val: Any):
                    self.val = val

                def __str__(self) -> str:
                    return f"{{{self.val}}}"

            def __init__(self, iterable: Iterable[Any]):
                self.nodes: Dict[Any, UnionFindSet.Node] = {}
                self.parent_map: Dict[UnionFindSet.Node, UnionFindSet.Node] = {}
                self.size_map: Dict[UnionFindSet.Node, int] = {}

                for item in iterable:
                    node = UnionFindSet.Node(item)
                    self.nodes[item] = node
                    self.parent_map[node] = node
                    # size默认为0表示没有初始化
                    self.size_map[node] = 0

            def find_head(self, node):
                """返回给定节点所在集合的头节点
                在找到头节点后, 会将node-head的链条扁平化
                """
                stack = []
                # 寻找node的头节点
                while node is not self.parent_map[node]:
                    stack.append(node)
                    node = self.parent_map[node]
                # 扁平化node-head的链条
                while stack:
                    self.parent_map[stack.pop()] = node
                return node

            def same(self, a, b):
                """判断两个值是否在同一个集合中"""
                return self.find_head(self.nodes[a]) == self.find_head(self.nodes[b])

            def union(self, a, b):
                """合并a, b所在的集合"""
                ahead = self.find_head(self.nodes[a])
                bhead = self.find_head(self.nodes[b])

                # 其中有一个没有初始化, 就停止操作, 我们要找的可是连着的1
                if self.size_map[ahead] == 0 or self.size_map[bhead] == 0:
                    return

                # a, b不在同一个集合中才需要合并
                if ahead is not bhead:
                    asize = self.size_map[ahead]
                    bsize = self.size_map[bhead]

                    largest_head, smaller_head = (ahead, bhead) if asize > bsize else (bhead, ahead)

                    # 将更小的集合连接到更大的集合头, 避免出现长链表
                    self.parent_map[smaller_head] = largest_head
                    self.size_map[largest_head] = asize + bsize

            def connect(self, x):
                # 之前没有连接过才需要连接
                if self.size_map[x] == 0:
                    self.parent_map[x] = x
                    self.size_map[x] = 1
                    for i, j in [(x[0] + 1, x[1]), (x[0] - 1, x[1]), (x[0], x[1] + 1), (x[0], x[1] - 1)]:
                        if 0 <= i < m and 0 <= j < n:
                            self.union(x, (i, j))
                return len(self)

            def __len__(self):
                return len(self.size_map)

            def __bool__(self):
                return len(self.size_map) > 0

        ufs = UnionFindSet([[(x, y)] for x in range(m) for y in range(n)])
        ans = []
        for pos in positions:
            ans.append(ufs.connect((pos[0], pos[1])))
        return ans
