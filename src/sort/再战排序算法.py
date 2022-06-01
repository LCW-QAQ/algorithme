import random
import unittest
from typing import List
from utils import algorithm_util as autils


@autils.perf_stdout()
def selection_sort(arr: List[int]):
    """选择排序"""
    for i in range(len(arr)):
        min_idx = i
        for j in range(i + 1, len(arr)):
            min_idx = j if arr[j] < arr[min_idx] else min_idx
        arr[i], arr[min_idx] = arr[min_idx], arr[i]


@autils.perf_stdout()
def bubble_sort(arr: List[int]):
    """冒泡排序"""
    n = len(arr)
    for i in range(0, n - 1):
        for j in range(n - 1, i, -1):
            if arr[j] < arr[i]:
                arr[i], arr[j] = arr[j], arr[i]


@autils.perf_stdout()
def insertion_sort(arr: List[int]):
    """插入排序"""
    n = len(arr)
    for i in range(1, n):
        for j in range(i, 0, -1):
            if arr[j] < arr[j - 1]:
                arr[j], arr[j - 1] = arr[j - 1], arr[j]


@autils.perf_stdout()
def shell_sort(arr: List[int]):
    """希尔"""
    n = len(arr)
    gap = 1
    while gap < n // 3:
        gap = gap * 3 + 1
    while gap > 0:
        # [1, 2, ..., gap, ..., n]
        for i in range(gap, n):
            # 向前看一个gap
            for j in range(i, gap - 1, -gap):
                if arr[j] < arr[j - gap]:
                    arr[j], arr[j - gap] = arr[j - gap], arr[j]
        gap = (gap - 1) // 3


@autils.perf_stdout()
def merge_sort(arr: List[int]):
    """归并"""

    def process(arr, l, r):
        if l >= r:
            return
        m = l + ((r - l) >> 1)
        process(arr, l, m)
        process(arr, m + 1, r)
        merge(arr, l, m, r)

    def merge(arr, l, m, r):
        temp = [0] * (r - l + 1)
        lp, rp, tp = l, m + 1, 0

        """
        [2,4,5] [1,7,8]
         l       r
        """

        while lp <= m and rp <= r:
            if arr[lp] <= arr[rp]:
                temp[tp] = arr[lp]
                lp += 1
            else:
                temp[tp] = arr[rp]
                rp += 1
            tp += 1

        while lp <= m:
            temp[tp] = arr[lp]
            lp += 1
            tp += 1

        while rp <= r:
            temp[tp] = arr[rp]
            rp += 1
            tp += 1

        arr[l:r + 1] = temp[:]

    process(arr, 0, len(arr) - 1)


@autils.perf_stdout()
def quick_sort(arr: List[int]):
    """快速"""

    def process(arr, l, r):
        if l >= r:
            return
        m = (l + r) // 2
        """
        [lt, eq, gt]
        """
        rand_idx = random.randint(l, r)
        arr[r], arr[rand_idx] = arr[rand_idx], arr[r]
        eq_l, eq_r = partition(arr, l, r)
        process(arr, l, eq_l - 1)
        process(arr, eq_r + 1, r)

    def partition(arr, l, r):
        """
        将数组arr以r轴，划分为[lt, eq, gt]区域
        :return (等与区域的左边界，等与区域的右边界)
        """
        lt, gt = l - 1, r
        pivot = arr[r]
        index = l
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

    process(arr, 0, len(arr) - 1)


@autils.perf_stdout()
def heap_sort(arr: List[int]):
    """堆"""

    def left_of(index):
        return index * 2 + 1

    def parent_of(index):
        return (index - 1) // 2

    # 向上堆化 大根堆插入
    def heap_insert(arr, index):
        while arr[index] > arr[parent_of(index)]:
            arr[index], arr[parent_of(index)] = arr[parent_of(index)], arr[index]
            index = parent_of(index)

    # 向下堆化 大根堆
    def heapify(arr, index, heap_size):
        left = left_of(index)
        while left < heap_size:
            # 找到两个节点中更大的那个
            greater_child_idx = left if left + 1 >= n else left if arr[left] > arr[left + 1] else left + 1
            greater_idx = index if arr[index] > arr[greater_child_idx] else greater_child_idx
            if greater_idx == index:
                break
            arr[index], arr[greater_idx] = arr[greater_idx], arr[index]
            index = greater_idx
            left = left_of(index)

    n = len(arr)
    # 从上向下建堆 O(N*logN)
    # for i in range(n):
    #     heap_insert(arr, i)
    # 从下向上建堆 O(N)
    for i in range(n-1, -1, -1):
        heapify(arr, i, n)
    # 将堆顶的最大值替换到数组最后（升序）
    arr[n - 1], arr[0] = arr[0], arr[n - 1]
    while n > 0:
        heapify(arr, 0, n)
        n -= 1
        arr[0], arr[n] = arr[n], arr[0]



class SortTest(unittest.TestCase):
    def test_sort(self):
        arr = autils.gen_random_arr(length=10000)
        # arr = autils.gen_random_arr(length=10)
        arr_std = sorted(arr)
        sort_funcs = [selection_sort, bubble_sort, insertion_sort, shell_sort, merge_sort, quick_sort, heap_sort]
        # sort_funcs = [selection_sort, bubble_sort, merge_sort]
        for func in sort_funcs:
            arr1 = arr.copy()
            func(arr1)
            self.assertTrue(arr_std == arr1)
