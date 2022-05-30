import random
import unittest
from typing import List

from utils import algorithm_util as autils


@autils.perf_stdout()
def selection_sort(arr: List[int]):
    """选择排序"""
    n = len(arr)
    for i in range(n):
        min_idx = i
        for j in range(i + 1, n):
            if arr[min_idx] > arr[j]:
                min_idx = j
        arr[min_idx], arr[i] = arr[i], arr[min_idx]


@autils.perf_stdout()
def bubble_sort(arr: List[int]):
    """冒泡排序"""
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - 1, i, -1):
            if arr[j] < arr[j - 1]:
                arr[j], arr[j - 1] = arr[j - 1], arr[j]


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
        for i in range(gap, n):
            for j in range(i, gap - 1, -gap):
                if arr[j] < arr[j - gap]:
                    arr[j], arr[j - gap] = arr[j - gap], arr[j]
        gap = (gap - 1) // 3


@autils.perf_stdout()
def merge_sort(arr: List[int]):
    """归并"""

    def process(arr, l, r):
        if l == r:
            return
        m = l + ((r - l) >> 1)
        process(arr, l, m)
        process(arr, m + 1, r)
        merge(arr, l, m, r)

    def merge(arr, l, m, r):
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
            lp += 1
            k += 1
        while rp <= r:
            temp[k] = arr[rp]
            rp += 1
            k += 1
        arr[l:r + 1] = temp[:]

    return process(arr, 0, len(arr) - 1)


@autils.perf_stdout()
def quick_sort(arr: List[int]):
    """快速"""

    def process(arr, l, r):
        if l >= r:
            return
        rand_idx = random.randint(l, r)
        arr[r], arr[rand_idx] = arr[rand_idx], arr[r]
        eq_l, eq_r = partition(arr, l, r)
        process(arr, l, eq_l - 1)
        process(arr, eq_r + 1, r)

    def partition(arr, l, r) -> (int, int):
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

    def heap_insert(arr, index):
        while arr[index] > arr[parent_of(index)]:
            arr[index], arr[parent_of(index)] = arr[parent_of(index)], arr[index]
            index = parent_of(index)

    def heapify(arr, index, size):
        left = left_of(index)
        while left < size:
            # 判断left + 1没越界的情况下，返回left + 1 和 left中更大的
            greater_child_idx = left if left + 1 >= size else left if arr[left] > arr[left + 1] else left + 1
            greater_idx = index if arr[index] > arr[greater_child_idx] else greater_child_idx
            if greater_idx == index:
                break
            arr[index], arr[greater_idx] = arr[greater_idx], arr[index]
            index = greater_idx
            left = left_of(index)

    n = len(arr)
    # for i in range(n):
    #     heap_insert(arr, i)

    for i in range(n - 1, -1, -1):
        heapify(arr, i, n)

    arr[0], arr[n - 1] = arr[n - 1], arr[0]
    while n > 0:
        heapify(arr, 0, n)
        arr[0], arr[n - 1] = arr[n - 1], arr[0]
        n -= 1


class SortTest(unittest.TestCase):
    def test_sort(self):
        arr = autils.gen_random_arr(length=10000)
        # arr = autils.gen_random_arr(length=10)
        arr_std = sorted(arr)
        sort_funcs = [selection_sort, bubble_sort, insertion_sort, shell_sort, merge_sort, quick_sort, heap_sort]
        for func in sort_funcs:
            arr1 = arr.copy()
            func(arr1)
            print(func.__name__)
            self.assertTrue(arr_std == arr1)

    def test_snippet(self):
        ITER_COUNT = 100000000

        @autils.perf_stdout()
        def f1():
            for i in range(ITER_COUNT):
                l = 1000
                r = 2000
                m = l + ((r - l) >> 1)

        @autils.perf_stdout()
        def f2():
            for i in range(ITER_COUNT):
                l = 1000
                r = 2000
                m = (l + r) // 2

        f1()
        f2()
