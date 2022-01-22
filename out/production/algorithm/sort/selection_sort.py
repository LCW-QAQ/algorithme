import sort_util


def sort(arr):
    for i in range(0, len(arr)):
        min_idx = i
        for j in range(i + 1, len(arr)):
            min_idx = j if arr[min_idx] > arr[j] else min_idx
        arr[i], arr[min_idx] = arr[min_idx], arr[i]


sort_util.check(sort)
