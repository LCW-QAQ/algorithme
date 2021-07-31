package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MergeSort {
    /**
     * 递归实现
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr.length <= 1) return;
        process(arr, 0, arr.length - 1);
    }

    /**
     * 迭代实现
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        if (arr.length <= 1) return;
        int step = 1;
        int n = arr.length;
        // 步长还够就继续
        while (step < n) {
            // 每次从最左边开始
            int l = 0;
            while (l < n) {
                // 步长已经够大了, 揍不了了退出
                if (step > n - l) {
                    break;
                }
                int m = l + step - 1;
                // 超出数组长度, 取最后索引
                int r = Math.min(m + step, n - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
            // 防止溢出
            if (step > n / 2) {
                break;
            }
            step <<= 1;
        }
    }

    static class Help {
        int l;
        int r;
        boolean lcompleted;
        boolean rcompleted;

        public Help(int l, int r, boolean lcompleted, boolean rcompleted) {
            this.l = l;
            this.r = r;
            this.lcompleted = lcompleted;
            this.rcompleted = rcompleted;
        }

        @Override
        public String toString() {
            return "Help{" +
                    "l=" + l +
                    ", r=" + r +
                    ", lcompleted=" + lcompleted +
                    ", rcompleted=" + rcompleted +
                    '}';
        }
    }

    /**
     * 递归改迭代
     * @param arr
     */
    public static void sort3(int[] arr) {
        if (arr.length <= 1) return;

        Stack<Help> stack = new Stack<>();
        stack.push(new Help(0, arr.length - 1, false, false));
        while (!stack.empty()) {
            Help help = stack.peek();
            // base case 子过程结束了
            if (help.l == help.r) {
                stack.pop();
                completeHelp(stack.peek());
                continue;
            }

            int mid = ((help.r - help.l) >> 1) + help.l;

            // 左边没有完成
            if (!help.lcompleted) {
                stack.push(new Help(help.l, mid, false, false));
                continue;
            }

            // 右边没有完成
            if (!help.rcompleted) {
                stack.push(new Help(mid + 1, help.r, false, false));
                continue;
            }

            merge(arr, help.l, mid, help.r);
            stack.pop();
            if (!stack.empty()) {
                completeHelp(stack.peek());
            }
        }
    }

    public static void completeHelp(Help help) {
        if (help.lcompleted) {
            help.rcompleted = true;
        } else {
            help.lcompleted = true;
        }
    }

    /**
     * 你想吧arr的l..r上排有序
     *
     * @param arr
     * @param l   左边界
     * @param r   右边界
     */
    private static void process(int[] arr, int l, int r) {
        // base case
        if (l == r) return;

        int mid = ((r - l) >> 1) + l;
        process(arr, l, mid);
        process(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int lp = l, rp = m + 1, tp = 0;
        while (lp <= m && rp <= r) {
            temp[tp++] = arr[lp] <= arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];
        System.arraycopy(temp, 0, arr, l, temp.length);
    }

    public static void main(String[] args) {
        int[] arr = Stream.generate(() -> new Random().nextInt(1000))
                .limit(10)
                .flatMapToInt(IntStream::of)
                .toArray();
        int[] std = arr.clone();
        sort3(arr);
        Arrays.sort(std);
        System.out.println(Arrays.equals(std, arr));
        System.out.println(Arrays.toString(std));
        System.out.println(Arrays.toString(arr));
    }
}
