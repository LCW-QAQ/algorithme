"""荷兰国旗问题
将给定区间内的数, 按照给定轴, 排成[<; =; >;]三个区域
"""


def dutch_flag(arr: list[int], l: int, r: int) -> tuple[int, int]:
    """将[l, r]范围上的数, 以r索引位置为轴, 排成[<r; =r; >r]
    :return: 返回等于区域的左右边界, 包含等于区域
    """
    if l == r:
        return l, r
    lt, gt, index = l - 1, r, l
    while index < gt:
        # 当前值小于轴, 将小于区的下一个与当前值交换, 当前继续
        if arr[index] < arr[r]:
            lt += 1
            arr[lt], arr[index] = arr[index], arr[lt]
            index += 1
        # 当前值大于轴, 将大于区域的前一项与当期值交换, 同时大于区向前移
        elif arr[index] > arr[r]:
            gt -= 1
            arr[index], arr[gt] = arr[gt], arr[index]
        else:
            index += 1
    arr[gt], arr[r] = arr[r], arr[gt]
    return lt + 1, gt


if __name__ == '__main__':
    arr = [8, 7, 32, 2, 2, 1, 3, 12, 11, 12, 7]
    print(dutch_flag(arr, 0, len(arr) - 1))
    print(arr)
