package binary_heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxLineCover {
    /**
     * 计算最多有多少条线段重合, 线段两端都是整数, 且重合长度必须>=1
     * 思路:
     * 可以判断包含0.5的线段有多少个, 实际上就是在求哪些线段穿过了0-1这个范围(所有线段两端都是整数)
     * 暴力判断每个点之间的小数点, 最后得出穿过线段最多的那个点有多少条线段
     *
     * @param lines 线段树组
     * @return 最大重合数
     */
    public static int maxCoverIter(int[][] lines) {
        // 所有线段中最小的起始位置
        int min = lines[0][0];
        // 所有线段中最大的起始位置
        int max = lines[0][1];
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int ans = 0;
        for (double i = min + 0.5; i < max; i += 0.5) {
            int cover = 0;
            for (int j = 0; j < lines.length; j++) {
                if (lines[j][0] < i && lines[j][1] > i) {
                    cover++;
                }
            }
            ans = Math.max(ans, cover);
        }
        return ans;
    }

    public static int maxCoverHeap(int[][] lines) {
        // 按每条线段的起始位置排序(默认升序)
        Arrays.sort(lines, Comparator.comparingInt(o -> o[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int ans = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i][0]) {
                heap.poll();
            }
            heap.offer(lines[i][1]);
            ans = Math.max(ans, heap.size());
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] lines = {{1, 2}, {2, 5}, {1, 8}, {6, 9}, {6, 7}, {5, 7}, {3, 4}};
        System.out.println(maxCoverIter(lines.clone()));
        System.out.println(maxCoverHeap(lines.clone()));
    }
}
