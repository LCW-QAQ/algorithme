# 在一个有序数组中，找>=某个数最左侧的位置
def upper_bound(arr, num):
    left, right = 0, len(arr) - 1
    ans = -1
    while left <= right:
        mid = ((right - left) >> 1) + left
        if arr[mid] >= num:
            ans = mid
            right = mid - 1
        else:
            left = mid + 1
    return ans


arr = [0, 1, 29, 30, 34, 40]
print(upper_bound(arr, 99))


# 在一个有序数组中，找<=某个数最右侧的位置
def lower_bound(arr, num):
    left, right = 0, len(arr) - 1
    ans = -1
    while left <= right:
        mid = ((right - left) >> 1) + left
        if arr[mid] <= num:
            ans = mid
            left = mid + 1
        else:
            right = mid - 1
    return ans


arr = [0, 1, 29, 30, 34, 40]
print(lower_bound(arr, 27))


# 局部最小值问题
def local_min(arr):
    '''
    给定一个数组, 相邻两数不相等
    如果i=0, arr[i] < arr[i + 1] 则i索引的数为局部最小值
    如果i=len(arr), arr[i] < arr[i - 1] 则i索引的数为局部最小值
    如果arr[i] < arr[i - 1] && arr[i] < arr[i + 1], (i > 0 && i < len(arr)), 则i索引的数为局部最小值
    '''
    if len(arr) == 0:
        return -1
    if len(arr) < 2:
        return 0
    if arr[0] < arr[1]:
        return 0
    elif arr[len(arr) - 1] < arr[len(arr) - 2]:
        return len(arr) - 1

    left, right = 0, len(arr) - 1
    ans = -1
    while left <= right:
        mid = ((right - left) >> 1) + left
        if arr[mid] > arr[mid + 1]:
            left = mid + 1
        elif arr[mid] > arr[mid - 1]:
            right = mid - 1
        else:
            ans = mid
            break
    return ans


arr = [10, 1, 2, 2, 3, 0, 1, 2, 11, 19]
print(local_min(arr))
