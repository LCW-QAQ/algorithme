"""
arr = [10,20,30]
有一个金条, 有sum(arr)克
需要分割金条, 只能按照arr数组中给定的值分割, 不在乎arr中的顺序
每次分割后需要花费金条当前总质量的铜板:
    例如 arr = [10,20,30], 当前质量是 60
    按照10分割一次, 花费60, 还剩下50
    再按20分割一次, 花费50, 还剩下30
    最后按30分割一次, 花费30, 分割完成
请你给出最省钱的分割方案
"""
from utils.algorithm_util import *
import heapq
from typing import List


@perf_stdout()
def less_money_split_gold_bars_by_recursion(arr: List[int]) -> int:
    if not arr:
        return 0

    def process(arr, sum_val):
        # 只剩一个的时候停下来
        if len(arr) == 1:
            return sum_val
        ans = float("inf")
        # 暴力枚举, 数组中每两个和在一起的情况
        for i in range(len(arr)):
            for j in range(i + 1, len(arr)):
                """
                这个是错的, 只有0..i   j+1..len    没有i,j中间的
                ans = min(ans, process(arr[:i] + [arr[i] + arr[j]] + arr[j + 1:], sum_val + arr[i] + arr[j]))
                """
                ans = min(ans,
                          process(arr[:i] + arr[i + 1:j] + arr[j + 1:] + [arr[i] + arr[j]], sum_val + arr[i] + arr[j]))
        return ans

    return process(arr, 0)


@perf_stdout()
def less_money_split_gold_bars_by_heap(arr: List[int]) -> int:
    if not arr:
        return 0
    heap = []
    for num in arr:
        heapq.heappush(heap, num)
    sum_val = 0
    while len(heap) > 1:
        cur = heapq.heappop(heap) + heapq.heappop(heap)
        sum_val += cur
        heapq.heappush(heap, cur)
    return sum_val


if __name__ == '__main__':
    for _ in range(10):
        arr = gen_random_arr(length=5)
        b = less_money_split_gold_bars_by_heap(arr)
        a = less_money_split_gold_bars_by_recursion(arr)
        if a != b:
            raise Exception(f"""
            a: {a}
            b: {b}
            arr: {arr}
            """)
