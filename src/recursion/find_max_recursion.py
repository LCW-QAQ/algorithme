"""
求数组arr[L..R]中的最大值，怎么用递归方法实现。

1）将[L..R]范围分成左右两半。左：[L..Mid]  右[Mid+1..R]
2）左部分求最大值，右部分求最大值
3） [L..R]范围上的最大值，是max{左部分最大值，右部分最大值}
"""
import timeit

import utils.algorithm_util as autils

import unittest


def max_recursion(arr):
    def process(arr, l, r):
        if l == r:
            return arr[l]
        mid = (l + r) // 2
        return max(process(arr, l, mid), process(arr, mid + 1, r))

    return process(arr, 0, len(arr) - 1)


class TestCase(unittest.TestCase):
    def test_(self):
        def foo():
            arr = autils.gen_random_arr()
            self.assertTrue(max(arr) == max_recursion(arr))
        timeit.timeit(foo, number=100)

