public class Main {

    private static final int ITER_COUNT = 100000000;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        fib(45);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static int fib(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}