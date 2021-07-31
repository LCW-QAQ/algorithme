import sort_util

def sort(arr):
    # 遍历整个数组
    for i in range(0, len(arr) - 1):
        # 从最后开始冒泡, 挨个比较, 比到i就可以停了, 因为i前面的都是拍好的
        for j in range(len(arr) - 1, i, -1):
            if arr[j] < arr[j - 1]:
                arr[j], arr[j - 1] = arr[j - 1], arr[j]


sort_util.check(sort)