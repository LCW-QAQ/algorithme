import collections
from dataclasses import dataclass
from utils.algorithm_util import *
from tkinter import *
from tkinter import ttk


class A:

    def __str__(self) -> str:
        return f"self: {id(self)}"


if __name__ == '__main__':
    print(A())