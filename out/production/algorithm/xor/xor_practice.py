# 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
def find_only_odd_num(arr):
    xor = 0
    for i in arr:
        xor ^= i
    return xor


arr = [1, 1, 1, 1, 2, 3, 3, 3, 2, 4, 4]
print(find_only_odd_num(arr))


# 怎么把一个int类型的数，提取出最右侧的1来
def get_rightmost_one(num):
    return num & -num


num = 10
print(
    f"num: {num}, num_bin: {bin(num)}, num_rightmost_num: {get_rightmost_one(num)}, num_rightmost_num_bin: {bin(get_rightmost_one(num))}")


# 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
def find_two_odd_num(arr):
    xor = 0
    for i in arr:
        xor ^= i
    # xor 就是那两个数异或运算的结果
    # xor 二进制位 最右边的1, 因为xor是异或的结果, 随意当前1位置上这两个数一定不一样
    xor_r_o = xor & -xor
    xorp = 0
    for num in arr:
        # num & xor_r_0 != 0 当前这个数最右边的1和我们要找的数最右边的1在同一个位置, 循环结束后, xorp就是其中一个数
        if num & xor_r_o != 0:
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
    bits_arr = [0] * 32
    for num in arr:
        for i in range(0, 32):
            bits_arr[i] += num >> i & 1
    ans = 0
    for i in range(0, 32):
        if bits_arr[i] % m != 0:
            ans |= 1 << i
    return ans


print(find_num_km([1, 1, 1, 2, 2, 2, 4, 4], 2, 3))
