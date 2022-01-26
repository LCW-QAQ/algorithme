# https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended/
import heapq
from typing import List


class Solution:
    def maxEvents(self, events: List[List[int]]) -> int:
        """最多可参加的会议数量
        典型的贪心题目
        """
        # 按开始时间排序
        events.sort()
        heap = []
        ans = 0
        # 开始时间
        begin = 1
        # events索引
        index = 0
        n = len(events)

        while index < n or heap:
            # 存储已近开始的会议, 并按结束时间加入小根堆
            while index < n and events[index][0] == begin:
                # 将结束时间加入小根堆
                heapq.heappush(heap, events[index][1])
                index += 1
            # 结束时间 < 开始时间, 弹出结束的会议
            while heap and heap[0] < begin:
                heapq.heappop(heap)

            if heap:
                # 选择结束时间最早的会议参加
                heapq.heappop(heap)
                ans += 1
            begin += 1

        return ans
