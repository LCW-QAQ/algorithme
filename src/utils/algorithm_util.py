import functools
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
