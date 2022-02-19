"""
题目:
给定一个整型数组arr，代表数值不同的纸牌排成一条线
玩家A和玩家B依次拿走每张纸牌
规定玩家A先拿，玩家B后拿
但是每个玩家每次只能拿走最左或最右的纸牌
玩家A和玩家B都绝顶聪明
请返回最后获胜者的分数。
扑克牌为非负数
"""
from typing import List
from utils.algorithm_util import perf_stdout, gen_random_arr


@perf_stdout
def win(pokers: List[int]) -> int:
    def pre(pokers, l, r):
        if l == r:
            return pokers[l]
        ans1 = pokers[l] + post(pokers, l + 1, r)
        ans2 = pokers[r] + post(pokers, l, r - 1)
        return max(ans1, ans2)

    def post(pokers, l, r):
        if l == r:
            return 0
        ans1 = pre(pokers, l + 1, r)
        ans2 = pre(pokers, l, r - 1)
        return min(ans1, ans2)

    if not pokers:
        return 0
    n = len(pokers)
    return max(pre(pokers, 0, n - 1), post(pokers, 0, n - 1))


@perf_stdout
def win_memory_search(pokers: List[int]) -> int:
    def pre(pokers, l, r, pre_map, post_map):
        if pre_map[l][r] != -1:
            return pre_map[l][r]
        ans = -1
        if l == r:
            ans = pokers[l]
        else:
            ans1 = pokers[l] + post(pokers, l + 1, r, pre_map, post_map)
            ans2 = pokers[r] + post(pokers, l, r - 1, pre_map, post_map)
            ans = max(ans1, ans2)
        pre_map[l][r] = ans
        return ans

    def post(pokers, l, r, pre_map, post_map):
        if post_map[l][r] != -1:
            return post_map[l][r]
        ans = -1
        if l == r:
            ans = 0
        else:
            ans1 = pre(pokers, l + 1, r, pre_map, post_map)
            ans2 = pre(pokers, l, r - 1, pre_map, post_map)
            ans = min(ans1, ans2)
        post_map[l][r] = ans
        return ans
    if not pokers:
        return 0
    n = len(pokers)
    pre_map, post_map = [
        [-1 for _ in range(n)] for _ in range(n)], [[-1 for _ in range(n)] for _ in range(n)]
    return max(pre(pokers, 0, n - 1, pre_map, post_map), post(pokers, 0, n - 1, pre_map, post_map))


@perf_stdout
def win_dp(pokers: List[int]) -> int:
    if not pokers:
        return 0
    n = len(pokers)
    pre_dp = [[0 for _ in range(n)] for _ in range(n)]
    post_dp = [[0 for _ in range(n)] for _ in range(n)]
    for i in range(n):
        pre_dp[i][i] = pokers[i]
    for col in range(1, n):
        l, r = 0, col
        while r < n:
            pre_dp[l][r] = max(pokers[l] + post_dp[l + 1][r],
                               pokers[r] + post_dp[l][r - 1])
            post_dp[l][r] = min(pre_dp[l + 1][r], pre_dp[l][r - 1])
            l += 1
            r += 1
    return max(pre_dp[0][n - 1], post_dp[0][n-1])


pokers = [5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7]
pokers = gen_random_arr(length=900)
print(win_memory_search(pokers))
print(win_dp(pokers))
# print(win(pokers))
