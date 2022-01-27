from typing import Iterable, Any, Dict


class Node:
    def __init__(self, val: Any):
        self.val = val


class UnionFindSet:
    def __init__(self, iterable: Iterable[Any]):
        self.nodes: Dict[Any, Node] = {}
        self.parent_map: Dict[Node, Node] = {}
        self.size_map: Dict[Node, int] = {}

        for item in iterable:
            node = Node(item)
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
