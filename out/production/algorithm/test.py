from utils.algorithm_util import *


def wrapper_class(cls=None):
    print(cls)

    def wrap(cls):
        return cls

    if cls is None:
        return wrap

    return wrap(cls)


@wrapper_class
class A:
    pass
