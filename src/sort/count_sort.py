from typing import List
import sort_util


def count_sort(arr: List[int], min_val: int, max_val: int):
    """计数排序
    用于明确知道数据范围的排序
    :param arr: 需要排序的数组
    :param min_val: 数组中的最小值
    :param max_val: 数组中的最大值
    """
    # 用于计数的数组
    count_arr = [0] * (max_val - min_val + 1)
    # 计数
    for num in arr:
        count_arr[num - min_val] += 1
    index = 0
    # i表示具体数字, count表示i数字出现的次数
    for i, count in enumerate(count_arr):
        for _ in range(count):
            arr[index] = i + min_val
            index += 1


if __name__ == '__main__':
    sort_util.check(count_sort, sort_util.DEFAULT_RANDOM_MIN_VAL, sort_util.DEFAULT_RANDOM_MAX_VAL)
