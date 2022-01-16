# 在一个有序数组中，找>=某个数最左侧的位置
def upper_bound(arr, target) -> int:
    ans = -1
    l, r = 0, len(arr) - 1
    while l <= r:
        mid = (l + r) // 2
        if arr[mid] >= target:
            r = mid - 1
            ans = mid
        else:
            l = mid + 1
    return ans


arr = [0, 1, 30, 30, 30, 40]
print(upper_bound(arr, 30))


# 在一个有序数组中，找<=某个数最右侧的位置
def lower_bound(arr, target):
    ans = -1
    l, r = 0, len(arr) - 1
    while l <= r:
        mid = (l + r) // 2
        if arr[mid] <= target:
            l = mid + 1
            ans = mid
        else:
            r = mid - 1
    return ans


arr = [0, 1, 29, 30, 34, 40]
print(lower_bound(arr, 27))


# 局部最小值问题
def local_min(arr) -> int:
    """
    给定一个数组, 相邻两数不相等
    如果i=0, arr[i] < arr[i + 1] 则i索引的数为局部最小值
    如果i=len(arr), arr[i] < arr[i - 1] 则i索引的数为局部最小值
    如果arr[i] < arr[i - 1] && arr[i] < arr[i + 1], (i > 0 && i < len(arr)), 则i索引的数为局部最小值
    """
    n = len(arr)
    if n == 0:
        return -1
    if n < 2:
        return 0
    if arr[0] < arr[1]:
        return 0
    elif arr[n - 1] < arr[n - 2]:
        return n - 1
    l, r = 0, n - 1
    while l <= r:
        mid = (l + r) // 2
        if arr[mid] > arr[mid + 1]:
            l = mid + 1
        elif arr[mid] > arr[mid - 1]:
            r = mid - 1
        else:
            return mid
    return -1


arr = [10, 1, 2, 2, 3, 0, 1, 2, 11, 19]
print(local_min(arr))
