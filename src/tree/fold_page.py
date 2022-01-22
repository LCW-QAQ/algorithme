"""
将纸条每次从下往上对折, 不要翻面, 求N次对折后, 纸条从上往下的折痕是什么(...凹..凸...)
"""


def process(height, n, down):
    """当前节点在第height层, 对折了n次即最多有n层, down == True表示凹, 反之表示凸"""
    if height > n:
        return
    process(height + 1, n, True)
    print("凹" if down is True else "凸", end=" ")
    process(height + 1, n, False)


def fold_page(n):
    process(1, n, True)


if __name__ == '__main__':
    fold_page(4)
