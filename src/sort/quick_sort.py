from utils.algorithm_util import *

"""快速排序
"""


def partition(arr: list[int], l: int, r: int) -> tuple[int, int]:
    """将arr, [l, r]范围上, 以r为轴排有序
    :return: 返回等与区域的左右边界, 包含等于区域
    """
    if l == r:
        return l, r
    pivot = arr[r]
    lt, gt, index = l - 1, r, l
    while index < gt:
        if arr[index] < pivot:
            lt += 1
            arr[index], arr[lt] = arr[lt], arr[index]
            index += 1
        elif arr[index] > pivot:
            gt -= 1
            arr[index], arr[gt] = arr[gt], arr[index]
        else:
            index += 1
    arr[gt], arr[r] = arr[r], arr[gt]
    return lt + 1, gt


def process(arr: list[int], l: int, r: int):
    """将arr, [l, r]范围上排有序"""
    # 这里是>=, 因为不一定每个区域都有值
    if l >= r:
        return
    rand_pivot_idx = random.randint(l, r)
    arr[r], arr[rand_pivot_idx] = arr[rand_pivot_idx], arr[r]
    eq_l, eq_r = partition(arr, l, r)
    process(arr, l, eq_l - 1)
    process(arr, eq_r + 1, r)


@perf_stdout
def quick_sort(arr: list[int]):
    if len(arr) < 2:
        return
    process(arr, 0, len(arr) - 1)


def quick_sort2(arr: list[int]):
    """
    分区时没有等于区的快排，写起来简单一些
    """

    def process2(arr, l, r):
        if l >= r:
            return
        rand_idx = random.randint(l, r)
        arr[rand_idx], arr[r] = arr[r], arr[rand_idx]

        lt, gt = l, r
        pivot = arr[r]
        while lt < gt:
            while arr[lt] <= pivot and lt < r:
                lt += 1
            while arr[gt] > pivot:
                gt -= 1
            if lt < gt:
                arr[lt], arr[gt] = arr[gt], arr[lt]
        arr[l], arr[gt] = arr[gt], arr[l]

        process(arr, l, gt - 1)
        process(arr, gt + 1, r)

    n = len(arr)
    if n < 2:
        return

    process2(arr, 0, n - 1)


if __name__ == '__main__':
    arr = gen_random_arr(1000000)
    a1, a2 = arr.copy(), arr.copy()
    a1.sort()
    quick_sort(a2)
    print(a1 == a2)
