import functools
import random
import time
from typing import *


def perf_stdout(fn):
    if not callable(fn):
        raise Exception("not a callable")

    @functools.wraps(fn)
    def wrapper(*args, **kwargs):
        start = time.perf_counter()
        res = fn(*args, **kwargs)
        print(f"function.__name__ = {fn.__name__}, 耗时 {time.perf_counter() - start}")
        return res

    return wrapper


def gen_random_arr(length: int = 100000, min_val: int = 0, max_val: int = 1000) -> List[int]:
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


def gen_random_alphabet_str_arr(min_str_len: int = 0, max_str_len: int = 10, length: int = 100) -> List[str]:
    """生成随机字符串数组, 字符串只包含大小写字母
    :param min_str_len: 字符串最小长度
    :param max_str_len: 字符串最大长度
    :param length: 随机数组长度
    """
    # 26个字母大小写
    alphas = [chr(x) for x in range(ord("a"), ord("z") + 1)] + [chr(x) for x in range(ord("A"), ord("Z") + 1)]
    # 打乱顺序
    random.shuffle(alphas)

    ans = []
    for _ in range(length):
        random_str = "".join(random.choices(alphas, k=random.randint(min_str_len, max_str_len)))
        ans.append(random_str)

    return ans
