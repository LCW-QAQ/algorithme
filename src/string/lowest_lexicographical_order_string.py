"""
给定一个字符串数组strs, 请拼接出字典序最小的字符串
"""
from utils.algorithm_util import *
from typing import List
from sortedcontainers import SortedList


@perf_stdout
def lowest_lexicographical_order_string_by_dfs_backtracking(strs):
    """回溯法, 返回全排列"""
    ans = SortedList()
    n = len(strs)

    def dfs(strs, i):
        """搜索回溯
        :param strs: 字符串数组
        :param i: 来到第几项
        """
        # 到最后一项了
        if i == n - 1:
            ans.add("".join(strs))
            return

        for j in range(i, n):
            strs[j], strs[i] = strs[i], strs[j]
            dfs(strs, i + 1)
            strs[j], strs[i] = strs[i], strs[j]

    dfs(strs, 0)

    return ans[0] if ans else ""


@perf_stdout
def lowest_lexicographical_order_string_by_iter(strs):
    """暴力解法, 穷举, 返回strs的全排列"""

    def process(strs):
        ans = SortedList()
        if not strs:
            # 没有的时候+空字符串, 不然就不会拼接了
            ans.add("")
            return ans

        for i, start in enumerate(strs):
            nexts = process(strs[0:i] + strs[i + 1:])
            for s in nexts:
                ans.add(start + s)

        return ans

    return process(strs)[0]


@perf_stdout
def lowest_lexicographical_order_string_by_greedy(strs):
    if not strs:
        return ""

    def cmp(x, y):
        # 注意, 比较时, x,y其中一项为"", 拼接时会返回0, 判断相等, 会出错.
        # 例如 `"A" + "" = "" + "A"`  `"" + "B" == "B" + ""` 得出错误结论 "A" == "B"
        # 所以在x,y为""时, 做特殊处理
        if x == "":
            return -1
        elif y == "":
            return 1
        a = x + y
        b = y + x
        return 0 if a == b else 1 if a > b else -1

    strs.sort(key=functools.cmp_to_key(cmp))
    return "".join(strs)


def lowest_lexicographical_order_string(strs: List[str]) -> str:
    """给定一个字符串数组strs, 请拼接出字典序最小的字符串
    直接以字典序, 排序, 是错的. 例如["b", "ba"]  字典序排完是"bba", 但是"bab" < "bba"
    贪心算法, 比较 a + b <= b + a
    """
    return lowest_lexicographical_order_string_by_dfs_backtracking(strs)


if __name__ == '__main__':
    for i in range(100):
        strs = gen_random_alphabet_str_arr(length=5)
        a = lowest_lexicographical_order_string_by_greedy(strs)
        b = lowest_lexicographical_order_string_by_iter(strs)
        c = lowest_lexicographical_order_string_by_dfs_backtracking(strs)
        if a != b or a != c:
            raise Exception(f"""
            a: {a},
            b: {b},
            c: {c}
            strs: {strs}
            """)
