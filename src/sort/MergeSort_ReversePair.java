package sort;

/**
 * 求给定数组中有几个右边比左边小的数
 * [i, arr.length) 范围上, arr[i + x] < arr[i], x > i
 */
public class MergeSort_ReversePair {
    public static void main(String[] args) {
        int[] arr = {2, -10, 100, 22, 23, 8, 87, 75};
        System.out.println(algorithm(arr.clone()) == algorithmIter(arr.clone()));
    }

    private static int algorithmIter(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    res += 1;
                }
            }
        }
        return res;
    }

    private static int algorithm(int[] arr) {
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
            ans += arr[lp] > arr[rp] ? r - rp + 1 : 0;
            temp[tp++] = arr[lp] > arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];
        System.arraycopy(temp, 0, arr, l, temp.length);
        return ans;
    }
}
