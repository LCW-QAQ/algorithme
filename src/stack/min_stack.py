"""
实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能

1）pop、push、getMin操作的时间复杂度都是 O(1)。

2）设计的栈类型可以使用现成的栈结构。
"""
import random


class MinStack:
    def __init__(self):
        self.__stack = []
        self.__min_stack = []

    def append(self, val):
        if self.__stack:
            top = self.__stack[-1]
            if val < top:
                self.__min_stack.append(val)
            else:
                self.__min_stack.append(self.__min_stack[-1])
        else:
            self.__min_stack.append(val)
        self.__stack.append(val)

    def pop(self):
        self.__min_stack.pop()
        return self.__stack.pop()

    def peek_min(self):
        return self.__min_stack[-1]

    def __str__(self):
        return str(self.__stack)


min_stack = MinStack()
min_stack.append(0)
min_stack.append(-9)
min_stack.append(1)
print(min_stack.peek_min())
