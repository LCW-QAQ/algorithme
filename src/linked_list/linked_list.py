class LinkedList:
    class __Node:
        def __init__(self, item=None) -> None:
            self.item = item
            self.prev = None
            self.next = None

    def __init__(self) -> None:
        self.__head = None
        self.__tail = None
        self.__size = 0

    def add(self, item):
        newNode = LinkedList.__Node(item)
        if self.__head is None:
            self.__head = self.__tail = newNode
        else:
            last = self.__tail
            last.next = newNode
            newNode.prev = last
            self.__tail = newNode
        self.__size += 1

    def addFirst(self, item):
        newNode = LinkedList.__Node(item)
        if self.__head is None:
            self.__head = self.__tail = newNode
        else:
            first = self.__head
            first.prev = newNode
            newNode.next = first
            self.__head = newNode
        self.__size += 1

    def popFirst(self):
        if self.__head is None:
            raise Exception("没有元素了")
        elif self.__head == self.__tail:
            result = self.__head.item
            self.__head = self.__tail = None
            return result
        else:
            result = self.__head.item
            self.__head.next.prev = None
            self.__head = self.__head.next
            return result

    def pop(self):
        if self.__head is None:
            raise Exception("没有元素了")
        elif self.__head == self.__tail:
            result = self.__head.item
            self.__head = self.__tail = None
            return result
        else:
            result = self.__tail.item
            last = self.__tail
            last.prev.next = None
            self.__tail = last.prev
            return result

    def remove(self, item):
        node = self.__head
        pre = None
        while node is not None:
            if node.item == item:
                pre.next = node.next
                node.next.prev = pre
                break
            pre = node
            node = node.next
        self.__size -= 1

    def reverse(self):
        node = self.__head
        pre = None
        while node is not None:
            next = node.next
            node.next = pre
            node.prev = next
            pre = node
            node = next
        self.__head, self.__tail = self.__tail, self.__head

    def __iter__(self):
        class Iter:
            def __init__(self, list) -> None:
                self.node = list._LinkedList__head

            def __next__(self):
                if self.node is not None:
                    result = self.node.item
                    self.node = self.node.next
                    return result
                else:
                    raise StopIteration

        return Iter(self)

    def size(self):
        return self.__size


l = LinkedList()
for i in range(0, 10):
    l.addFirst(i)
for item in l:
    print(item, end=" ")
print("\n-------------------")
l.remove(8)
l.popFirst()
l.reverse()
for item in l:
    print(item, end=" ")
print("\n-------------------")
l.reverse()
for item in l:
    print(item, end=" ")