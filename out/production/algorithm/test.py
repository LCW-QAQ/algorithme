from utils.algorithm_util import *

ITER_COUNT = 1000000


@perf_stdout
def a():
    s = "hello"
    for i in range(ITER_COUNT):
        s += "hello"
    return len(s)


@perf_stdout
def b():
    s = "hello"
    for i in range(ITER_COUNT):
        s = "".join([s, "hello"])
    return len(s)


@perf_stdout
def c():
    s = "hello"
    for i in range(ITER_COUNT):
        s = f"{s}hello"
    return len(s)


if __name__ == '__main__':
    assert a() == b() == c()
