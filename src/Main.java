public class Main {
    public static void main(String[] args) {
        foo(8);
    }

    public static void foo(int n) {
        int[][] arr = new int[n][];

        arr[0] = new int[]{1};
        for (int i = 1; i < n; i++) {
            arr[i] = new int[i + 1];
            arr[i][0] = 1;
            arr[i][i] = 1;
            for (int j = 1; j < i; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i - 1][j - 1];
            }
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < n - i; j++) {
                System.out.printf("%-2s", "");
            }
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%4s", arr[i][j]);
            }
            System.out.println();
        }
    }
}