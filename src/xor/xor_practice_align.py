# 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数(出现奇数次的数)
def find_only_odd_num(arr):
    xor = 0
    for item in arr:
        xor ^= item
    return xor


arr = [1, 1, 1, 1, 2, 3, 3, 3, 2, 4, 4]
print(find_only_odd_num(arr))


# 怎么把一个int类型的数，提取出最右侧的1来
def get_rightmost_one(num):
    return num & -num


num = 10
print(
    f"num: {num}, num_bin: {bin(num)},"
    f" num_rightmost_num: {get_rightmost_one(num)},"
    f" num_rightmost_num_bin: {bin(get_rightmost_one(num))}"
)


# 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
def find_two_odd_num(arr):
    xor = 0
    for num in arr:
        xor ^= num
    right_one = get_rightmost_one(xor)
    xorp = 0
    for num in arr:
        if num & right_one != 0:
            xorp ^= num
    return xorp, xorp ^ xor


arr = [1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 0, 0, 77, 77]
print(find_two_odd_num(arr))


# 出现任意K, M次
def find_num_km(arr, k, m):
    """
    一个数组中有一种数出现K次，其他数都出现了M次，
    M > 1,  K < M
    找到，出现了K次的数，
    要求，额外空间复杂度O(1)，时间复杂度O(N)
    """
    bit_arr = [0] * 32
    for n in arr:
        for i in range(32):
            # 这个不行的, n & (1 << i) 结果是0或其他数
            # arr[i] += n & (1 << i)
            bit_arr[i] += (n >> i) & 1
    ans = 0
    for i, n in enumerate(bit_arr):
        if n % m != 0:
            ans |= 1 << i
    return ans


print(find_num_km([1, 1, 1, 2, 2, 2, 4, 4], 2, 3))
