from typing import List
import sort_util


def shell_sort_half_gap(arr: List[int]):
    """希尔排序"""
    gap = len(arr) // 2
    # 间隔变化
    while gap > 0:
        # 从间隔项, 依次向后遍历
        for i in range(gap, len(arr)):
            # 获取间隔项的前一个间隔, 依次类推
            for j in range(i, gap - 1, -gap):
                if arr[j] < arr[j - gap]:
                    arr[j], arr[j - gap] = arr[j - gap], arr[j]
        gap //= 2


def shell_sort_knuth_gap(arr: List[int]):
    gap = 1
    while gap < len(arr) // 3:
        gap = gap * 3 + 1
    while gap > 0:
        for i in range(gap, len(arr)):
            for j in range(i, gap - 1, -gap):
                if arr[j] < arr[j - gap]:
                    arr[j], arr[j - gap] = arr[j - gap], arr[j]
        gap = (gap - 1) // 3


if __name__ == '__main__':
    sort_util.check(shell_sort_half_gap)
    sort_util.check(shell_sort_knuth_gap)
