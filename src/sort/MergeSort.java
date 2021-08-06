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
     * @param arr 数组
     */
    public static void sort(int[] arr) {
        if (arr.length <= 1) return;
        process(arr, 0, arr.length - 1);
    }

    /**
     * 迭代实现
     *
     * @param arr 数组
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

    /**
     * 一定要记录左右是否完成, 因为归并的条件是两个有序数组归并, 所以一定要等待左右都排好了才能merge
     */
    static class Help {
        int l; // 左边界
        int r; // 右边界
        boolean lcompleted; // 左边是否完成
        boolean rcompleted; // 右边是否完成

        public Help(int l, int r) {
            this.l = l;
            this.r = r;
        }

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

        /**
         * 左右边子数组, 完成排序
         * 我们优先判断左边是否完成, 那么在栈迭代中, 也必须先判断左边, 完成右边的前提是左边完成了
         */
        public void complete() {
            if (lcompleted) {
                rcompleted = true;
            } else {
                lcompleted = true;
            }
        }
    }

    /**
     * 归并排序 栈迭代 by myself
     *
     * @param arr 需要排序的数组
     */
    public static void sort4(int[] arr) {
        if (arr.length < 2) return;

        Stack<Help> stack = new Stack<>();
        stack.push(new Help(0, arr.length - 1));
        while (!stack.empty()) {
            Help help = stack.peek();

            // 子过程已完成, 只有一个元素
            if (help.l == help.r) {
                stack.pop();
                // 告诉栈顶, 他的左右某一个子过程完成了
                stack.peek().complete();
                continue;
            }

            int m = (help.l + help.r) / 2;

            // 左边没完成, 就继续压栈左边
            if (!help.lcompleted) {
                stack.push(new Help(help.l, m));
                continue;
            }

            // 右边没完成, 就继续压栈右边
            if (!help.rcompleted) {
                stack.push(new Help(m + 1, help.r));
                continue;
            }

            // 两边都排好了, 当然直接merge
            merge(arr, help.l, m, help.r);
            // 当前区间已经merge, 没用了
            stack.pop();
            // 告诉栈顶, 你的左右某一边完成了
            if (!stack.empty()) stack.peek().complete();
        }
    }

    /**
     * 栈迭代
     *
     * @param arr 数组
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
                .limit(10000)
                .flatMapToInt(IntStream::of)
                .toArray();
        int[] std = arr.clone();
        sort4(arr);
        Arrays.sort(std);
        System.out.println(Arrays.equals(std, arr));
    }
}
