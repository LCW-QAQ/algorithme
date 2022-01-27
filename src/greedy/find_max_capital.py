"""
输入正数组: items
正数: K, M
items[i][0] 表示i号项目的花费
items[i][1] 表示i号项目在扣除花费后还能挣到的钱
K 表示你只能串行的做K个项目
M 表示你的初始资金

求最多能挣多少?
"""
from utils.algorithm_util import *
from typing import List
from sortedcontainers import SortedList


def find_max_capital_by_recursion(items: List[List[int]], k: int, m: int) -> int:
    """暴力递归, 寻找最大资金方案"""

    def process(items, k, m):
        if k == 0:
            return m
        ans = m
        # 初始资金足够, 可以做的项目, 记录下来
        i_can_do_items = [(i, x) for i, x in enumerate(items) if x[0] <= m]
        for i, item in i_can_do_items:
            # 把能做的每个都先做一遍, 然后继续, 选出资金最高的ans
            ans = max(ans, process(items[:i] + items[i + 1:], k - 1, m + item[1]))
        return ans

    return process(items, k, m)


def find_max_capital(items: List[List[int]], k: int, m: int) -> int:
    heap_min_cost = SortedList(items, key=lambda x: x[0])
    heap_max_profit = SortedList(key=lambda x: -x[1])

    # 串行做项目
    for _ in range(k):
        # 将可以做的项目全部放到, 最大利润堆中
        while heap_min_cost and heap_min_cost[0][0] <= m:
            heap_max_profit.add(heap_min_cost.pop(0))
        # 成本不够做任何项目了, 返回
        if not heap_max_profit:
            return m
        # 做利润最大的项目, 记得弹出
        m += heap_max_profit.pop(0)[1]
    return m


if __name__ == '__main__':
    for _ in range(100):
        arr = gen_random_dimensional2_arr(2, 4, 0, 100)
        a = find_max_capital(arr, 3, 50)
        b = find_max_capital_by_recursion(arr, 3, 50)
        if a != b:
            print(f"""
            arr: {arr}
            a: {a}
            b: {b}
            """)
