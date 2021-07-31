package recursion;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Stream;

public class FindMaxInArr {
    public static int getMaxRecursion(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return arr[l];
        }
        int mid = ((r - l) >> 1) + l;
        int lMax = process(arr, l, mid);
        int rMax = process(arr, mid + 1, r);
        return Math.max(lMax, rMax);
    }

    public static int getMaxRecursionIter(int[] arr) {
        class Help {
            int l;
            int r;

            public Help(int l, int r) {
                this.l = l;
                this.r = r;
            }
        }
        Stack<Help> stack = new Stack<>();
        stack.push(new Help(0, arr.length - 1));
        int max = Integer.MIN_VALUE;
        while (!stack.isEmpty()) {
            Help help = stack.pop();
            if (help.l == help.r) {
                max = Math.max(max, arr[help.l]);
            } else {
                int mid = (help.l + help.r) / 2;
                stack.push(new Help(mid + 1, help.r));
                stack.push(new Help(help.l, mid));
            }
        }
        return max;
    }

    public static int getMaxIter(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            max = Math.max(max, num);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = Stream.generate(() -> new Random().nextInt(100))
                .mapToInt(value -> value)
                .limit(100)
                .toArray();
        System.out.println(Arrays.toString(arr.clone()));
        System.out.println(getMaxRecursion(arr.clone()) == getMaxIter(arr.clone()));
        System.out.println(getMaxRecursionIter(arr.clone()) == getMaxIter(arr.clone()));
    }
}
