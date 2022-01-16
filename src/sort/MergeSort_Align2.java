package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-16
 */
public class MergeSort_Align2 {
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

    static class Info {
        int l;
        int r;
        boolean ldone;
        boolean rdone;

        public Info(int l, int r) {
            this.l = l;
            this.r = r;
        }

        /**
         * 先排左边再排右边
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
        Stack<Info> stack = new Stack<>();
        stack.push(new Info(0, arr.length - 1));
        while (!stack.empty()) {
            // 不能直接pop, 还不知道有没有归并完
            final Info info = stack.peek();
            // base case
            // 三个if都要continue, 必须保证两边都排完了再merge
            if (info.l == info.r) {
                stack.pop();
                stack.peek().done();
                continue;
            }

            int mid = (info.l + info.r) / 2;

            if (!info.ldone) {
                stack.push(new Info(info.l, mid));
                continue;
            }

            if (!info.rdone) {
                stack.push(new Info(mid + 1, info.r));
                continue;
            }

            merge(arr, info.l, mid, info.r);
            stack.pop();
            if (!stack.empty()) {
                stack.peek().done();
            }
        }
    }

    private static void sortIter(int[] arr) {
        int step = 1;
        while (step < arr.length) {
            // 左边界
            int l = 0;
            while (l < arr.length) {
                // 防止中点越界
                if (step > arr.length - l) {
                    break;
                }
                // 中点
                int m = l + step - 1;
                // 防止右边界越界
                int r = Math.min(m + step, arr.length - 1);
                merge(arr, l, m, r);
                // l继续下一个序列, 来到r右边
                l = r + 1;
            }
            // 防止step越界
            if (step > (arr.length / 2)) {
                break;
            }
            step *= 2;
        }
    }

    private static void sortIter2(int[] arr) {
        for (int step = 1; step < arr.length; step *= 2) {
            for (int l = 0; l < arr.length; ) {
                // 防止中点越界
                if (l + step > arr.length) {
                    break;
                }
                int m = l + step - 1;
                int r = Math.min(m + step, arr.length - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
        }
    }

    private static void sortRecursion(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = (l + r) / 2;
        process(arr, l, mid);
        process(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int lp = l, rp = m + 1, k = 0;

        while (lp <= m && rp <= r) {
            temp[k++] = arr[lp] <= arr[rp] ? arr[lp++] : arr[rp++];
        }
        while (lp <= m) {
            temp[k++] = arr[lp++];
        }
        while (rp <= r) {
            temp[k++] = arr[rp++];
        }
        System.arraycopy(temp, 0, arr, l, temp.length);
    }
}