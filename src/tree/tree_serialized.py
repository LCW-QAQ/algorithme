# Definition for a binary tree node.
from collections import deque


class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


class Codec:
    def serialize(self, root):
        """Encodes a tree to a single string.

        :type root: TreeNode
        :rtype: str
        """
        return self.level_serialize(root)

    def deserialize(self, data):
        """Decodes your encoded data to tree.

        :type data: str
        :rtype: TreeNode
        """
        return self.level_deserialize(data)

    def pre_serialize(self, root):
        """按照二叉树先序, 序列化"""
        ans = []
        self.pres(root, ans)
        return ",".join(ans)

    def pres(self, node, ans):
        if not node:
            # `.` 表示空节点
            ans.append(".")
        else:
            ans.append(str(node.val))
            self.pres(node.left, ans)
            self.pres(node.right, ans)

    def pre_deserialize(self, data):
        """根据字符串, 生成二叉树"""
        if not data:
            return None
        return self.pred(list(reversed(data.split(","))))

    def pred(self, data_list):
        """根据data字符串生成二叉树"""
        val = data_list.pop()
        if not val:
            return None
        head = TreeNode(int(val))
        head.left = self.pred(data_list)
        head.right = self.pred(data_list)
        return head

    def level_serialize(self, root):
        """按层序列化二叉树"""
        ans = []
        if root is None:
            ans.append(".")
        else:
            q = deque()
            q.append(root)
            ans.append(str(root.val))
            while q:
                node = q.popleft()
                if node.left is not None:
                    ans.append(str(node.left.val))
                    q.append(node.left)
                else:
                    ans.append(".")
                if node.right is not None:
                    ans.append(str(node.right.val))
                    q.append(node.right)
                else:
                    ans.append(".")
        return ",".join(ans)

    def level_deserialize(self, data):
        """根据字符串(按层遍历)反序列化二叉树"""

        def gen_node(s):
            return TreeNode(int(s)) if s != "." else None

        data_list = deque(data.split(","))
        if not data_list:
            return None
        head = gen_node(data_list.popleft())
        # bfs queue
        q = deque()
        if head is not None:
            q.append(head)
        while q:
            node = q.popleft()
            node.left = gen_node(data_list.popleft())
            node.right = gen_node(data_list.popleft())
            if node.left is not None:
                q.append(node.left)
            if node.right is not None:
                q.append(node.right)
        return head
