"""
计算最多有多少条线段重合, 线段两端都是整数, 且重合长度必须>=1
"""
import sortedcontainers as stc


def max_line_cover_iter(lines: list[list[int]]) -> int:
    # min, max 第一条线段的起始位置与结束位置
    min_val = lines[0][0]
    max_val = lines[0][1]
    # 找到所有线段中最小的起始位置与最大的结束位置, 线段的范围
    for line in lines:
        min_val = min(min_val, line[0])
        max_val = max(max_val, line[1])
    ans = 0
    # 找到所有经过x.5的线段, 找到经过x.5最多的线段
    i = min_val + 0.5
    while i < max_val:
        cover = 0
        for line in lines:
            if line[0] < i < line[1]:
                cover += 1
        ans = max(ans, cover)
        i += 0.5
    return ans


def max_line_cover(lines: list[list[int]]) -> int:
    lines.sort()
    ans = 0
    # 新建一个小根堆
    s = stc.SortedList()
    for line in lines:
        # 若s还有值(里面的值代表之前线段的结尾), 且之前线段的结尾 <= 当前线段的起始位置, 自然不会重合, 弹出
        while s and s[0] <= line[0]:
            s.pop(0)
        # 将当前线段的结束位置加入
        s.add(line[1])
        # len(s) 计算以当前线段的起始位置开始有多少条线段穿过了它
        ans = max(ans, len(s))
    return ans


if __name__ == '__main__':
    lines = [[1, 2], [2, 5], [1, 8], [6, 9], [6, 7], [5, 7], [3, 4]]
    print(max_line_cover_iter(lines.copy()))
    print(max_line_cover(lines.copy()))
