package recursion;

public class BinarySearch {
    public static int binarySearch(int[] arr, int target) {
        return process(arr, 0, arr.length - 1, target);
    }

    private static int process(int[] arr, int l, int r, int target) {
        int mid = ((r - l) >> 1) + l;
        // base case
        if (l > r) return -1;
        if (arr[mid] < target) {
            return process(arr, mid + 1, r, target);
        } else if (arr[mid] > target) {
            return process(arr, l, mid, target);
        } else {
            return mid;
        }
    }
}
