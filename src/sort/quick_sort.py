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


if __name__ == '__main__':
    arr = gen_random_arr(1000000)
    a1, a2 = arr.copy(), arr.copy()
    a1.sort()
    quick_sort(a2)
    print(a1 == a2)
