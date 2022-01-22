import time
import random
from typing import Callable

LEVEL_SIMPLE = 10000
LEVEL_HARD = 1000000
LEVEL_DIFF = 10000000

DEFAULT_RANDOM_MIN_VAL = 0
DEFAULT_RANDOM_MAX_VAL = 100000


def check(sort_func: Callable, *args, level=LEVEL_SIMPLE):
    """简单测试排序算法是否正确, 生成随机数数组, 排序"""
    arr = [random.randint(DEFAULT_RANDOM_MIN_VAL, DEFAULT_RANDOM_MAX_VAL) for _ in range(level)]
    arr_ = arr.copy()
    start = time.perf_counter()
    arr_.sort()
    print(f"sort: {time.perf_counter() - start}")
    start = time.perf_counter()
    sort_func(arr, *args)
    print(f"{sort_func.__name__}: {time.perf_counter() - start}")
    flag = arr_ == arr
    if not flag:
        if len(arr) < 100:
            print("arr:", arr)
            print("arr_:", arr_)
        raise Exception(f"{sort_func.__name__} verify error")


def check_hard(sort_func):
    check(sort_func, LEVEL_HARD)
