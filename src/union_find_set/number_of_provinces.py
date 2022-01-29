from collections import deque
from typing import List


# https://leetcode-cn.com/problems/number-of-provinces/submissions/
class Solution:
    def findCircleNum(self, isConnected: List[List[int]]) -> int:
        return self.union_set(isConnected)

    def bfs(self, isConnected):
        n = len(isConnected)
        visited = [False] * n
        ans = 0
        for i in range(n):
            if not visited[i]:
                q = deque([i])
                while q:
                    a = q.popleft()
                    visited[a] = True
                    for j in range(n):
                        if isConnected[a][j] == 1 and not visited[j]:
                            q.append(j)
                ans += 1
        return ans

    def dfs(self, isConnected):
        def process(isConnected, visited, i, n):
            for j in range(n):
                if isConnected[i][j] == 1 and not visited[j]:
                    visited[j] = True
                    process(isConnected, visited, j, n)

        n = len(isConnected)
        visited = [False] * n
        ans = 0

        for i in range(n):
            if not visited[i]:
                process(isConnected, visited, i, n)
                ans += 1
        return ans

    def union_set(self, isConnected):
        def find(parent, x):
            while parent[x] != x:
                x = parent[x]
            return x

        def union(parent, i, j):
            parent[find(parent, i)] = find(parent, j)

        n = len(isConnected)
        parent = [x for x in range(n)]
        ans = 0

        for i in range(n):
            for j in range(i + 1, n):
                if isConnected[i][j] == 1:
                    union(parent, i, j)

        for i, p in enumerate(parent):
            if i == p:
                ans += 1
        return ans
