"""求给定数组中有几个右边比左边小, 逆序对的数量
[i, arr.length) 范围上, arr[i + x] < arr[i], x > i
"""


def merge(arr: [int], l: int, m: int, r: int) -> int:
    """将arr[l:r]排好序的同时, 返回逆序对数量"""
    ans = 0
    temp = [0] * (r - l + 1)
    lp, rp, k = l, m + 1, 0

    while lp <= m and rp <= r:
        # 当左边大于右边的时候, 由于数组有序, rp后面的数也都比lp小, 所以rp剩下的包括rp都可以组成逆序对即`r - rp + 1`
        ans += r - rp + 1 if arr[lp] > arr[rp] else 0
        # 逆序对在排序时, 必须使用降序, 这样才能保证逆序对的相对顺序
        temp[(k, k := k + 1)[0]] = arr[(lp, lp := lp + 1)[0]] if arr[lp] > arr[rp] else arr[(rp, rp := rp + 1)[0]]
    while lp <= m:
        temp[(k, k := k + 1)[0]] = arr[(lp, lp := lp + 1)[0]]
    while rp <= r:
        temp[(k, k := k + 1)[0]] = arr[(rp, rp := rp + 1)[0]]
    arr[l:r + 1] = temp
    return ans


def process(arr: list[int], l: int, r: int):
    if l == r:
        return 0
    m = (l + r) // 2
    return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r)


def reverse_pair(arr):
    return process(arr, 0, len(arr) - 1)


def reverse_pair_iter(arr):
    ans = 0
    for i in range(1, len(arr)):
        for j in range(i):
            if arr[j] > arr[i]:
                ans += 1
    return ans


if __name__ == '__main__':
    arr = [2, -10, 100, 22, 23, 8, 87, 75]
    print(reverse_pair_iter(arr.copy()) == reverse_pair(arr.copy()))
