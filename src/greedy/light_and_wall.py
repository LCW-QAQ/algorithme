"""
给你一个字符串
X 表示墙
. 表示居民区
一盏灯可以的点亮 i位置与i+1, i-1位置
需要吧所有居民区都点亮, 至少需要多少盏灯
"""


def min_light(s: str):
    index = 0
    ans = 0
    n = len(s)
    while index < n:
        if s[index] == "X":
            index += 1
        else:
            ans += 1
            # 注意别越界
            if index + 1 == n:
                break
            else:
                if s[index + 1] == "X":
                    index += 2
                else:
                    index += 3
    return ans
