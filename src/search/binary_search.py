def search(arr, target):
    left, right = 0, len(arr) - 1
    ans = -1
    while left <= right:
        mid = ((right - left) >> 1) + left
        if arr[mid] < target:
            left = mid + 1
        elif arr[mid] > target:
            right = mid - 1
        else:
            ans = mid
            break
    return ans


arr = [i for i in range(0, 100)]
assert(arr.index(78) == search(arr, 78))