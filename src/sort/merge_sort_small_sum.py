"""求给定数组中, 所有左边小于右边的数的和(0 <= i < len, [0, i)比[i]小的数的和)
[0, i)范围上, arr[i-x] < arr[i], 所有满足条件数的和, x < i
"""


def merge(arr: list[int], l: int, m: int, r: int) -> int:
    """将arr[l:r]排好序的同时, 返回小和"""
    ans = 0
    temp = [0] * (r - l + 1)
    lp, rp, k = l, m + 1, 0

    while lp <= m and rp <= r:
        """
        当左边比右边小的时候, 右边所有比他大的数, 得到当前这个数在[l,r]范围上是否是小和
            如果arr[lp]在是这个数组的小和, 看它是多少个数的小和即`r - rp + 1`
            若不是不用管它
        """
        ans += (r - rp + 1) * arr[lp] if arr[lp] < arr[rp] else 0
        temp[(k, k := k + 1)[0]] = arr[(lp, lp := lp + 1)[0]] if arr[lp] <= arr[rp] else arr[(rp, rp := rp + 1)[0]]
    while lp <= m:
        temp[(k, k := k + 1)[0]] = arr[(lp, lp := lp + 1)[0]]
    while rp <= r:
        temp[(k, k := k + 1)[0]] = arr[(rp, rp := rp + 1)[0]]
    arr[l:r + 1] = temp
    return ans


def process(arr: list[int], l: int, r: int) -> int:
    """返回arr[l:r]的小和"""
    if l == r:
        # 左边没数返回0
        return 0
    m = (l + r) // 2
    return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r)


def small_sum(arr: list[int]) -> int:
    """归并排序实现
    无序 | 小和 小于右边数的和 小于 排序
    归并, 快排
    尝试使用快排解决小和问题, 结果: 无法使用快排实现, 归并的过程是两个有序数组, 同时满足之前的相对顺序, 而快排无法保证
    """
    return process(arr, 0, len(arr) - 1)


def small_sum_iter(arr: [int]) -> int:
    ans = 0
    for i in range(1, len(arr)):
        for j in range(i):
            if arr[j] < arr[i]:
                ans += arr[j]
    return ans


if __name__ == '__main__':
    arr = [2, -10, 100, 22, 23, 8, 87, 75]
    print(small_sum_iter(arr.copy()) == small_sum(arr))
