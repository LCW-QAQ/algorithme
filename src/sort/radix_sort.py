from typing import List
import sort_util


def find_max_bits(arr: List[int]) -> int:
    """返回给定数组最大数的位数"""
    max_val = max(arr)
    bits = 0
    while max_val > 0:
        max_val //= 10
        bits += 1
    return bits


def get_digit(num: int, i: int) -> int:
    """获取num的第i位的数, i从0索引开始"""
    return num // 10 ** i % 10


def radix_sort_process(arr: List[int], l: int, r: int, max_bits: int):
    """基数排序将[l,r]范围排有序, 其中最数的位数为max_bits
    :param arr: 需要排序的数组
    :param l: 左边界
    :param r: 右边界
    :param max_bits: 数组中最大数的位数
    """
    temp = [0] * (r - l + 1)
    # 遍历最大位数次
    for i in range(max_bits):
        # 创建0..10基数数组
        radix_arr = [0] * 10
        for num in arr:
            radix_arr[get_digit(num, i)] += 1
        # 构建前缀和数组
        for j in range(1, len(radix_arr)):
            radix_arr[j] += radix_arr[j - 1]
        # 反向迭代数组, 获取num对应前缀和数组中的我位置
        for j in range(r, l - 1, -1):
            radix_arr[get_digit(arr[j], i)] -= 1
            temp[radix_arr[get_digit(arr[j], i)]] = arr[j]
        arr[l:r + 1] = temp


def radix_sort(arr: List[int]):
    """基数排序
    """
    radix_sort_process(arr, 0, len(arr) - 1, find_max_bits(arr))


if __name__ == '__main__':
    sort_util.check(radix_sort)
