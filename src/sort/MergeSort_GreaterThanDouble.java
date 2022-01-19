package sort;

/**
 * 找出给定数组中, 左边比右边大两倍的数有多少个
 * arr[i] &gt; arr[i + x] * 2, x &gt; i
 */
public class MergeSort_GreaterThanDouble {

    public static int algorithmIter(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j] * 2) {
                    res += 1;
                }
            }
        }
        return res;
    }

    public static int algorithm(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) return 0;
        int m = ((r - l) >> 1) + l;
        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int lp = l, rp = m + 1, tp = 0;

        // 滑动窗口指针, 用来记录左边比右边的两倍大的值
        int ans = 0, windowR = m + 1;
        for (int i = l; i <= m; i++) {
            // 满足条件就一直滑
            while (windowR <= r && arr[i] > arr[windowR] * 2) {
                windowR++;
            }
            ans += windowR - m - 1;
        }

        while (lp <= m && rp <= r) {
            temp[tp++] = arr[lp] < arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];
        System.arraycopy(temp, 0, arr, l, temp.length);
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {8, 2, 1, 7, 4, 2, 3};
        System.out.println(algorithm(arr.clone()));
        System.out.println(algorithm(arr.clone()) == algorithmIter(arr.clone()));
    }
}