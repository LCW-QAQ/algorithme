package sort;

/**
 * 求给定数组中, 所有左边小于右边的数的和
 * [0, i)范围上, arr[i-x] < arr[i], 所有满足条件数的和, x < i
 */
public class MergeSort_SmallSum {
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
        int ans = 0;
        while (lp <= m && rp <= r) {
            ans += arr[lp] < arr[rp] ? (r - rp + 1) * arr[lp] : 0;
            temp[tp++] = arr[lp] < arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];
        System.arraycopy(temp, 0, arr, l, temp.length);
        return ans;
    }

    public static int algorithmIter(int[] arr) {
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    res += arr[j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {2, -10, 100, 22, 23, 8, 87, 75};
        System.out.println(algorithm(arr.clone()) == algorithmIter(arr.clone()));
    }
}
