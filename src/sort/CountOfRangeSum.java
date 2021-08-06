package sort;

/**
 * 给定一个数组arr, 两个整数lower, upper
 * 返回数组中有多少个子数组的累加和在[lower, upper]上
 */
public class CountOfRangeSum {

    public static int algorithm(int[] arr, int lower, int upper) {
        if (arr == null || arr.length < 1) return 0;
        long[] preSum = new long[arr.length];
        preSum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        return process(preSum, 0, arr.length - 1, lower, upper);
    }

    private static int process(long[] arr, int l, int r, int lower, int upper) {
        if (l == r) {
            if (arr[l] >= lower && arr[l] <= upper) {
                return 1;
            } else {
                return 0;
            }
        }
        int m = ((r - l) >> 1) + l;
        return process(arr, l, m, lower, upper)
                + process(arr, m + 1, r, lower, upper)
                + merge(arr, l, m, r, lower, upper);
    }

    private static int merge(long[] arr, int l, int m, int r, int lower, int upper) {
        // sliding window
        int res = 0;
        // 窗口范围 [winL, winR)
        int winL = l, winR = l;
        for (int i = m + 1; i <= r; i++) {
            // 求出范围值
            long max = arr[i] - lower;
            long min = arr[i] - upper;

            // 左边界是不满足条件即<min时再滑
            while (winL <= m && arr[winL] < min) {
                winL++;
            }

            // 右边界是<=max就可以滑
            while (winR <= m && arr[winR] <= max) {
                winR++;
            }

            res += winR - winL;
        }


        // sort
        long[] temp = new long[r - l + 1];
        int lp = l, rp = m + 1, tp = 0;
        while (lp <= m && rp <= r) {
            temp[tp++] = arr[lp] <= arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];
        System.arraycopy(temp, 0, arr, l, temp.length);
        return res;
    }

    public static int algorithmIterPreSum(int[] nums, int lower, int upper) {
        long[] preSum = new long[nums.length];
        preSum[0] = nums[0];
        // 前缀和数组
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = nums[i] + preSum[i - 1];
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                long sum;
                // 在前缀和数组中找到对应的值
                if (i != 0) {
                    sum = preSum[j] - preSum[i - 1];
                } else {
                    sum = preSum[j];
                }
                if (sum >= lower && sum <= upper) {
                    res += 1;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(algorithm(new int[]{-1, 1}, 0, 0));
    }
}
