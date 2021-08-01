package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MergeSort_Align {
    public static void main(String[] args) {
        int[] arr = Stream.generate(() -> new Random().nextInt(1000))
                .limit(1000000)
                .flatMapToInt(IntStream::of)
                .toArray();
        int[] std = arr.clone();
        int[] arr1 = arr.clone();
        int[] arr2 = arr.clone();
        int[] arr3 = arr.clone();
        int[] arr4 = arr.clone();
        Arrays.sort(std);
        sortRecursion(arr1);
        sortIter(arr2);
        sortRecurStack(arr3);
        sortIter2(arr4);
        System.out.println("sortRecursion: " + Arrays.equals(std, arr1));
        System.out.println("sortIter: " + Arrays.equals(std, arr2));
        System.out.println("sortIter2: " + Arrays.equals(std, arr4));
        System.out.println("sortRecurStack: " + Arrays.equals(std, arr3));
    }

    static class Help {
        int l;
        int r;
        boolean ldone;
        boolean rdone;

        public Help(int l, int r) {
            this.l = l;
            this.r = r;
            this.ldone = false;
            this.rdone = false;
        }

        /**
         * 这个方法先判断L还是R, 决定了我们那边先完成, if 里面continue了(我们先处理完一边再去处理另一边)
         */
        public void done() {
            if (ldone) {
                rdone = true;
            } else {
                ldone = true;
            }
        }
    }

    private static void sortRecurStack(int[] arr) {
        if (arr.length < 2) return;

        Stack<Help> stack = new Stack<>();
        // [0..(len - 1)]范围 动态规划
        stack.push(new Help(0, arr.length - 1));

        while (!stack.empty()) {
            Help help = stack.peek();

            // 子过程完成了
            if (help.l == help.r) {
                stack.pop();
                // 告诉前一个过程, 你其中一边完成了
                stack.peek().done();
                continue;
            }

            int mid = ((help.r - help.l) >> 1) + help.l;

            if (!help.ldone) {
                stack.push(new Help(help.l, mid));
                continue;
            }

            if (!help.rdone) {
                stack.push(new Help(mid + 1, help.r));
                continue;
            }

            merge(arr, help.l, mid, help.r);
            stack.pop();
            if (!stack.empty()) {
                stack.peek().done();
            }
        }
    }

    private static void sortIter(int[] arr) {
        if (arr.length < 2) return;

        int step = 1;
        int n = arr.length;

        while (step < n) {
            int l = 0;
            while (l < n) {
                if (step > n - l) break; // 步长已经足够长了, 步长比剩下的元素都要长break
                int m = l + step - 1;
                // if (m >= n) break;
                int r = Math.min(m + step, n - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
            if (step > (n >> 1)) break;
            step <<= 1;
        }
    }

    private static void sortIter2(int[] arr) {
        if (arr.length < 2) return;

        int n = arr.length;
        for (int step = 1; step < n; step <<= 1) {
            for (int l = 0; l < n;) {
                if (step > n - l) break;
                int m = l + step - 1;
                int r = Math.min(m + step, n - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
        }
    }

    private static void sortRecursion(int[] arr) {
        if (arr.length < 2) return;

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        // base case
        if (l == r) return;

        int m = ((r - l) >> 1) + l;
        process(arr, l, m);
        process(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];

        int lp = l, rp = m + 1, tp = 0;
        while (lp <= m && rp <= r) {
            temp[tp++] = arr[lp] <= arr[rp] ? arr[lp++] : arr[rp++];
        }

        while (lp <= m) temp[tp++] = arr[lp++];
        while (rp <= r) temp[tp++] = arr[rp++];

        System.arraycopy(temp, 0, arr, l, temp.length);
    }


}