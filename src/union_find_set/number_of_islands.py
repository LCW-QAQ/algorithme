# https://leetcode-cn.com/problems/number-of-islands/
from collections import deque
from typing import List, Any, Iterable, Dict


class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        return self.union_set(grid)

    def union_set(self, grid):
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
                    self.size_map[node] = 1

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

                # a, b不在同一个集合中才需要合并
                if ahead is not bhead:
                    asize = self.size_map[ahead]
                    bsize = self.size_map[bhead]

                    largest_head, smaller_head = (ahead, bhead) if asize > bsize else (bhead, ahead)

                    # 将更小的集合连接到更大的集合头, 避免出现长链表
                    self.parent_map[smaller_head] = largest_head
                    self.size_map[largest_head] = asize + bsize
                    # smallerHead 不在是头节点, 删除器size信息
                    self.size_map.pop(smaller_head)

            def __len__(self):
                return len(self.size_map)

            def __bool__(self):
                return len(self.size_map) > 0

        rows = len(grid)
        cols = len(grid[0])

        # 初始化并查集
        ufs = UnionFindSet([(i, j) for i in range(rows) for j in range(cols) if grid[i][j] == "1"])
        # 初始化第一行
        for i in range(1, cols):
            if grid[0][i - 1] == grid[0][i] == "1":
                ufs.union((0, i - 1), (0, i))
        # 初始化第一列
        for i in range(1, rows):
            if grid[i - 1][0] == grid[i][0] == "1":
                ufs.union((i - 1, 0), (i, 0))
        for i in range(1, rows):
            for j in range(1, cols):
                if grid[i][j] == "1":
                    if grid[i][j - 1] == "1":
                        ufs.union((i, j), (i, j - 1))
                    if grid[i - 1][j] == "1":
                        ufs.union((i, j), (i - 1, j))
        return len(ufs)

    def bfs(self, grid):
        ans = 0
        rows = len(grid)
        cols = len(grid[0])
        for i in range(rows):
            for j in range(cols):
                if grid[i][j] == "1":
                    grid[i][j] = "0"
                    ans += 1
                    q = deque([(i, j)])
                    while q:
                        row, col = q.popleft()
                        for x, y in [(row - 1, col), (row + 1, col), (row, col - 1), (row, col + 1)]:
                            if 0 <= x < rows and 0 <= y < cols and grid[x][y] == "1":
                                grid[x][y] = "0"
                                q.append((x, y))
        return ans

    def dfs(self, grid):
        def process(grid, i, j):
            """将i,j位置的上下左右一直扩展直到整片岛屿"""
            if i < 0 or i == len(grid) or j < 0 or j == len(grid[0]) or grid[i][j] != "1":
                return
            grid[i][j] = "#"
            process(grid, i - 1, j)
            process(grid, i + 1, j)
            process(grid, i, j + 1)
            process(grid, i, j - 1)

        ans = 0
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == "1":
                    process(grid, i, j)
                    ans += 1

        return ans
