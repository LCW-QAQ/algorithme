from utils.algorithm_util import *

import random
import sortedcontainers as stc
import queue
import collections
import heapq
import bisect


def fib(n):
    if n == 1 or n == 2:
        return 1
    return fib(n - 1) + fib(n - 2)


@perf_stdout
def test():
    return fib(45)


if __name__ == '__main__':
    print(test())
