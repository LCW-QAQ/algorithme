
from collections import deque
import unittest


class QueueByStack:
    def __init__(self):
        self.stack = []
        self.stack_help = []
    
    def put(self, val):
        self.stack.append(val)
    
    def get(self):
        while len(self.stack) > 1:
            self.stack_help.append(self.stack.pop())
        res = self.stack.pop()
        while self.stack_help:
            self.stack.append(self.stack_help.pop())
        return res
    
    def __str__(self) -> str:
        return str(self.stack)

    def __bool__(self) -> bool:
        return bool(self.stack)


class TestCase(unittest.TestCase):
    def test_(self):
        q = QueueByStack()
        deq = deque()
        for i in range(10):
            q.put(i)
            deq.append(i)
        while q:
            self.assertTrue(q.get() == deq.popleft())
        