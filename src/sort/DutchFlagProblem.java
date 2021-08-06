package sort;

import java.util.Arrays;

public class DutchFlagProblem {
    /**
     * 给定区间内的数, 按照给定轴, 排成[&lt;, =, &gt;]
     *
     * @param arr 目标数组
     * @param l   左边界
     * @param r   右边界, 同时也是轴(目标数)
     * @return 返回等于区域的左右边界
     */
    public static int[] dutchFlag(int[] arr, int l, int r) {
        if (l > r) return new int[]{-1, -1};
        if (l == r) return new int[]{l, r};
        int lt = l - 1, gt = r, index = l;
        while (index < gt) {
            if (arr[index] < arr[r]) {
                swap(arr, ++lt, index++);
            } else if (arr[index] > arr[r]) {
                swap(arr, --gt, index);
            } else {
                index++;
            }
        }
        swap(arr, gt, r);
        return new int[]{lt + 1, gt};
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 7, 32, 2, 2, 1, 3, 12, 11, 12, 7};
        System.out.println(Arrays.toString(dutchFlag(arr, 0, arr.length - 1)));
        System.out.println(Arrays.toString(arr));
    }
}