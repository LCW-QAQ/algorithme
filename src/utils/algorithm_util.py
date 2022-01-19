import functools
import random
import time


def perf_stdout(fn):
    if not callable(fn):
        raise Exception("not a function")

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        start = time.perf_counter()
        res = fn(*args, **kwargs)
        print(f"function.__name__ = {fn.__name__}, 耗时 {time.perf_counter() - start}")
        return res

    return wrapper


def gen_random_arr(length: int = 100000, min_val: int = 0, max_val: int = 1000) -> list[int]:
    """生成指定长度的随机数组
    :param length: 随机数组的长度
    :param min_val: 随机数的最小值, 包含min_val
    :param max_val: 随机数的最大值, 包含max_val
    :return:
    """
    arr = [0] * length
    for i in range(length):
        arr[i] = random.randint(min_val, max_val)
    return arr
