from copy import deepcopy
import time

LEVEL_SIMPLE = 10000
LEVEL_HARD = 1000000
LEVEL_DIFF = 10000000


def check(sort_func, level=LEVEL_SIMPLE):
    arr = [i for i in range(level, 0, -1)]
    arr_ = deepcopy(arr)
    start = time.perf_counter() 
    arr_.sort()
    print(f"sort: {time.perf_counter() - start}")
    start = time.perf_counter()
    sort_func(arr)
    print(f"sort_func: {time.perf_counter() - start}")
    flag = arr_ == arr
    if flag:
        print(True)
    else:
        raise Exception("sort_func error")


def check_hard(sort_func):
    check(sort_func, LEVEL_HARD)
