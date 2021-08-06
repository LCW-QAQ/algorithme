package sort;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class QuickSort {
    /**
     * 递归快排
     *
     * @param arr 需要排序的数组
     */
    public static void sort(int[] arr) {
        if (arr.length < 2) return;
        process(arr, 0, arr.length - 1);
    }

    /**
     * 将数组l..r范围上排有序
     *
     * @param arr 数组
     * @param l   左边界
     * @param r   右边界
     */
    private static void process(int[] arr, int l, int r) {
        if (l >= r) return;

        swap(arr, r, l + (int) (Math.random() * (r - l + 1)));
        int[] part = partition(arr, l, r);
        process(arr, l, part[0] - 1);
        process(arr, part[1] + 1, r);
    }

    /**
     * 将数组l..r范围上排成[&lt;, =, &gt;]
     *
     * @param arr 需要操作的数组
     * @param l   左边界
     * @param r   右边界 默认为轴
     * @return 等于区的左右边界
     */
    public static int[] partition(int[] arr, int l, int r) {
        if (l == r) return new int[]{l, r};
        int pivot = arr[r];
        int lt = l - 1, gt = r, index = l;
        while (index < gt) {
            if (arr[index] < pivot) {
                swap(arr, ++lt, index++);
            } else if (arr[index] > pivot) {
                swap(arr, --gt, index);
            } else {
                index++;
            }
        }
        swap(arr, gt, r);
        return new int[]{lt + 1, gt};
    }

    /**
     * 交换数组中a, b索引的值
     *
     * @param arr 数组
     * @param a   a索引
     * @param b   b索引
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * 手写栈, 快排
     *
     * @param arr 需要排序的数组
     */
    public static void sortStack(int[] arr) {
        class Help {
            int l, r;

            public Help(int l, int r) {
                this.l = l;
                this.r = r;
            }
        }
        if (arr == null || arr.length < 2) return;
        int n = arr.length;
        swap(arr, n - 1, (int) (Math.random() * n));
        int[] part = partition(arr, 0, n - 1);
        Stack<Help> stack = new Stack<>();
        stack.push(new Help(0, part[0] - 1));
        stack.push(new Help(part[1] + 1, n - 1));
        while (!stack.isEmpty()) {
            Help help = stack.pop();
            if (help.l < help.r) {
                swap(arr, help.r, help.l + (int) (Math.random() * (help.r - help.l + 1)));
                part = partition(arr, help.l, help.r);
                stack.push(new Help(help.l, part[0] - 1));
                stack.push(new Help(part[1] + 1, help.r));
            }
        }
    }

    public static void sortStack2(int[] arr) {
        class Help {
            int l, r;

            public Help(int l, int r) {
                this.l = l;
                this.r = r;
            }
        }
        if (arr == null || arr.length < 2) return;
        Stack<Help> stack = new Stack<>();
        swap(arr, arr.length - 1, (int) (Math.random() * arr.length));
        stack.push(new Help(0, arr.length - 1));
        while (!stack.isEmpty()) {
            Help help = stack.pop();
            if (help.l < help.r) {
                swap(arr, help.r, help.l + (int) (Math.random() * (help.r - help.l + 1)));
                int[] part = partition(arr, help.l, help.r);
                stack.push(new Help(help.l, part[0] - 1));
                stack.push(new Help(part[1] + 1, help.r));
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = Stream.generate(() -> new Random().nextInt(1000))
                .limit(10000)
                .flatMapToInt(IntStream::of)
                .toArray();
        int[] std = arr.clone();
        sortStack2(arr);
        Arrays.sort(std);
        System.out.println(Arrays.equals(std, arr));
    }
}
