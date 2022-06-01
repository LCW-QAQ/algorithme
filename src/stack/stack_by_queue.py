import unittest
from queue import SimpleQueue


class StackByQueue:
    def __init__(self):
        self.q = SimpleQueue()
        self.q_help = SimpleQueue()

    def append(self, val):
        self.q.put_nowait(val)

    def pop(self):
        while self.q.qsize() > 1:
            self.q_help.put_nowait(self.q.get_nowait())
        res = self.q.get_nowait()
        self.q, self.q_help = self.q_help, self.q
        return res

    def __bool__(self):
        return self.q.qsize() > 0


class TestCase(unittest.TestCase):
    def test_(self):
        stack = StackByQueue()
        stack_std = []
        for i in range(10):
            stack.append(i)
            stack_std.append(i)
        while stack:
            self.assertTrue(stack.pop() == stack_std.pop())
