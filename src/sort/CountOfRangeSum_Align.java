package sort;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-17
 */
public class CountOfRangeSum_Align {
    /**
     * 区间和的个数
     * 前缀和 + 归并排序
     * 区间和 => 前缀和
     * 指定范围内 => 顺序 => 归并排序
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] preSum = new long[nums.length + 1];
        long sum = 0;
        // 计算前缀和
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }
        return process(preSum, lower, upper, 0, preSum.length - 1);
    }

    private int process(long[] preSum, int lower, int upper, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = (l + r) / 2;
        int lRes = process(preSum, lower, upper, l, m);
        int rRes = process(preSum, lower, upper, m + 1, r);
        int ans = lRes + rRes;

        int i = l;
        int left = m + 1, right = m + 1;
        while (i <= m) {
            while (left <= r && preSum[left] - preSum[i] < lower) {
                left++;
            }
            while (right <= r && preSum[right] - preSum[i] <= upper) {
                right++;
            }
            ans += right - left;
            i++;
        }

        long[] sorted = new long[r - l + 1];
        int lp = l, rp = m + 1, k = 0;
        while (lp <= m && rp <= r) {
            sorted[k++] = preSum[lp] <= preSum[rp] ? preSum[lp++] : preSum[rp++];
        }
        while (lp <= m) sorted[k++] = preSum[lp++];
        while (rp <= r) sorted[k++] = preSum[rp++];
        System.arraycopy(sorted, 0, preSum, l, sorted.length);
        return ans;
    }
}