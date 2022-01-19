"""找出给定数组中, 左边比右边大两倍的数有多少个
arr[i] > arr[i + x] * 2, x > i
"""


def merge(arr: list[int], l: int, m: int, r: int) -> int:
    ans = 0
    wr = m + 1
    for i in range(l, m + 1):
        while wr <= r and arr[i] > arr[wr] * 2:
            wr += 1
        ans += wr - m - 1

    temp = [0] * (r - l + 1)
    lp, rp, k = l, m + 1, 0
    while lp <= m and rp <= r:
        if arr[lp] <= arr[rp]:
            temp[k] = arr[lp]
            lp += 1
        else:
            temp[k] = arr[rp]
            rp += 1
        k += 1
    while lp <= m:
        temp[k] = arr[lp]
        k += 1
        lp += 1
    while rp <= r:
        temp[k] = arr[rp]
        k += 1
        rp += 1
    arr[l:r + 1] = temp
    return ans


def process(arr: list[int], l: int, r: int) -> int:
    """求数组[l,r]范围上有多少个, 满足题意的结果"""
    if l == r:
        return 0
    m = (l + r) // 2
    return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r)


def grater_than_double(arr: list[int]) -> int:
    """
    左边比右边的两倍大, 这样的数有几个
    右边的两倍比左边小, 这样的数有几个
    数组 + 大小关系 + 求满足关系的结果 => 配合排序算法    
    """
    return process(arr, 0, len(arr) - 1)


def grater_than_double_iter(arr: list[int]) -> int:
    """暴力解法"""
    ans = 0
    for i in range(1, len(arr)):
        for j in range(i):
            if arr[i] * 2 < arr[j]:
                ans += 1
    return ans


if __name__ == '__main__':
    arr = [8, 2, 1, 7, 4, 2, 3]
    print(grater_than_double_iter(arr.copy()) == grater_than_double(arr.copy()))
