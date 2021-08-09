package binary_heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*
    给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op
    两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
    arr = [ 3   ,   3   ,   1   ,  2,      1,      2,      5…
    op = [ T   ,   T,      T,     T,      F,      T,       F…
    依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品，1用户退货了一件商品，2用户购买了一件商品，5用户退货了一件商品…

    一对arr[i]和op[i]就代表一个事件：
    用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品
    op[i] == F就代表这个用户退货了一件商品
    现在你作为电商平台负责人，你想在每一个事件到来的时候，
    都给购买次数最多的前K名用户颁奖。
    所以每个事件发生后，你都需要一个得奖名单（得奖区）。

    得奖系统的规则：
    1，如果某个用户购买商品数为0，但是又发生了退货事件，
         则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
    2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
    3，每次都是最多K个用户得奖，K也为传入的参数
          如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
    4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，
          一定在这两个区域中的一个
    5，购买数最大的前K名用户进入得奖区，
          在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
    6，如果购买数不足以进入得奖区的用户，进入候选区
    7，如果候选区购买数最多的用户，已经足以进入得奖区，
         该用户就会替换得奖区中购买数最少的用户（大于才能替换），
         如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
         如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
    8，候选区和得奖区是两套时间，`
         因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
         从得奖区出来进入候选区的用户，得奖区时间删除，
         进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
         从候选区出来进入得奖区的用户，候选区时间删除，
         进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）`
    9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
         离开是指彻底离开，哪个区域也不会找到该用户
         如果下次该用户又发生购买行为，产生>0的购买数，
         会再次根据之前规则回到某个区域中，进入区域的时间重记

    请遍历arr数组和op数组，遍历每一步输出一个得奖名单

    public List<List<Integer>>  topK (int[] arr, boolean[] op, int k)
*/
public class DaddyTopK {
    static class Customer {
        // 用户ID
        int id;
        // 购买数量
        int buyNum;
        // 事件发生的时间
        int time;

        public Customer(int id) {
            this.id = id;
        }
    }

    /**
     * 不优化, 求出得奖名单
     *
     * @param arr 用户id数组
     * @param op  用户操作 True为购买 False退货
     * @param k   得奖区用户数
     * @return 每一个时间点得奖的用户列表[.., [id1, id2, di3, ...], ..]
     */
    public List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> customerMap = new HashMap<>();
        ArrayList<Customer> candidates = new ArrayList<>();
        ArrayList<Customer> daddies = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean isBuy = op[i];
            // 处理无效事件, 用户购买数为0, 然后用户又退货了, 忽略该事件
            if (!isBuy && !customerMap.containsKey(id)) {
                ans.add(getCurDaddies(daddies));
                continue;
            }

            // 来到这里说明是有效事件, 操作可能有几种:
            // buyNum == 0, op == True
            // buyNum > 0, op == False
            // buyNum > 0, op == True

            //如果没有就创建一个用户
            if (!customerMap.containsKey(id)) {
                customerMap.put(id, new Customer(id));
            }
            Customer c = customerMap.get(id);
            if (isBuy) {
                c.buyNum++;
            } else {
                c.buyNum--;
            }
            // 购买数为0时, 删除用户
            if (c.buyNum == 0) customerMap.remove(id);

            // 你不在任何区域, 说明你是新用户
            if (!candidates.contains(c) && !daddies.contains(c)) {
                c.time = i;
                // 候选区有位子, 你直接成为爹, 没有位置先进入候选区, 后面再做处理
                if (daddies.size() < k) {
                    daddies.add(c);
                } else {
                    candidates.add(c);
                }
            }

            // 删除购买数为0的人
            candidates.forEach(item -> {
                if (item.buyNum == 0) {
                    candidates.remove(item.id);
                }
            });
            daddies.forEach(item -> {
                if (item.buyNum == 0) {
                    daddies.remove(item.id);
                }
            });

            // 默认升序, 最有可能进入, 候选区的人排最后面
            candidates.sort((o1, o2) -> o1.buyNum != o2.buyNum ? o1.buyNum - o2.buyNum : o2.time - o1.time);
            // 最有可能被候选区替换的人排后面
            daddies.sort((o1, o2) -> o1.buyNum != o2.buyNum ? o2.buyNum - o1.buyNum : o2.time - o1.time);

            move(candidates, daddies, k, i);
        }
        return ans;
    }

    /**
     * 看看候选区的人是否可以进入得奖区
     *
     * @param candidates 候选区
     * @param daddies    得奖区
     * @param k          得奖区的容量
     * @param time       时间点
     */
    private void move(ArrayList<Customer> candidates, ArrayList<Customer> daddies, int k, int time) {
        if (candidates.isEmpty()) return;
        // 得奖区没满, 候选区也不是空的, 出现这种情况说明之前得奖区可能有人无了
        if (daddies.size() < k) {
            Customer c = candidates.get(candidates.size() - 1);
            c.time = time;
            daddies.add(c);
            candidates.remove(candidates.size() - 1);
        } else { // 得奖区满了, 候选区有东西
            // 候选区购买最早且购买最多的人, 比得奖区后买最早且购买最少的人, 购买的还多, 那么你去吧得奖区那个人替换了
            if (candidates.get(candidates.size() - 1).buyNum > daddies.get(daddies.size() - 1).buyNum) {
                Customer oldDaddy = daddies.get(daddies.size() - 1);
                Customer newDaddy = candidates.get(candidates.size() - 1);
                daddies.remove(daddies.size() - 1);
                candidates.remove(candidates.size() - 1);
                oldDaddy.time = time;
                newDaddy.time = time;
                daddies.add(newDaddy);
                candidates.add(oldDaddy);
            }
        }
    }

    public List<Integer> getCurDaddies(List<Customer> daddies) {
        return daddies.stream().mapToInt(daddy -> daddy.id).boxed().collect(Collectors.toList());
    }

    /**
     * 优化后使用二叉堆, 获取得奖名单
     *
     * @param arr 用户id数组
     * @param op  用户操作 True为购买 False退货
     * @param k   得奖区用户数
     * @return 每一个时间点得奖的用户列表[.., [id1, id2, di3, ...], ..]
     */
    public static List<List<Integer>> topK_Good(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whosYourDaddy = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whosYourDaddy.operate(arr[i], op[i], i);
            ans.add(whosYourDaddy.getDaddies());
        }
        return ans;
    }

    private static class WhosYourDaddy {

        private HashMap<Integer, Customer> customerMap;
        private HeapGreater<Customer> daddiesHeap;
        private HeapGreater<Customer> candidateHeap;
        private int daddyLimit;

        public WhosYourDaddy(int daddyLimit) {
            customerMap = new HashMap<>();
            candidateHeap = new HeapGreater<>((o1, o2)
                    -> o1.buyNum != o2.buyNum ? o2.buyNum - o1.buyNum : o1.time - o2.time);
            daddiesHeap = new HeapGreater<>((o1, o2)
                    -> o1.buyNum != o2.buyNum ? o1.buyNum - o2.buyNum : o1.time - o2.time);
            this.daddyLimit = daddyLimit;
        }

        /**
         * 处理一个事件
         *
         * @param id    用户id
         * @param isBuy 用户操作
         * @param time  事件时间点
         */
        public void operate(int id, boolean isBuy, int time) {
            // 无效事件
            if (!isBuy && !customerMap.containsKey(id)) return;
            // 创建新用户
            if (!customerMap.containsKey(id)) customerMap.put(id, new Customer(id));
            Customer c = customerMap.get(id);
            if (isBuy) {
                c.buyNum++;
            } else {
                c.buyNum--;
            }
            // 购买数为0, 可以走了
            if (c.buyNum == 0) customerMap.remove(id);

            if (!candidateHeap.contains(c) && !daddiesHeap.contains(c)) {
                c.time = time;
                if (daddiesHeap.size() < daddyLimit) {
                    daddiesHeap.push(c);
                } else {
                    candidateHeap.push(c);
                }
            } else if (candidateHeap.contains(c)) {
                if (c.buyNum == 0) {
                    candidateHeap.remove(c);
                } else {
                    candidateHeap.resign(c);
                }
            } else {
                if (c.buyNum == 0) {
                    daddiesHeap.remove(c);
                } else {
                    daddiesHeap.resign(c);
                }
            }
            move2(time);
        }

        /**
         * 候选区移动到得奖区
         *
         * @param time
         */
        private void move2(int time) {
            if (candidateHeap.empty()) {
                return;
            }
            if (daddiesHeap.size() < daddyLimit) {
                Customer p = candidateHeap.pop();
                p.time = time;
                daddiesHeap.push(p);
            } else {
                if (candidateHeap.peek().buyNum > daddiesHeap.peek().buyNum) {
                    Customer oldDaddy = daddiesHeap.pop();
                    Customer newDaddy = candidateHeap.pop();
                    oldDaddy.time = time;
                    newDaddy.time = time;
                    daddiesHeap.push(newDaddy);
                    candidateHeap.push(oldDaddy);
                }
            }
        }

        public List<Integer> getDaddies() {
            return null;
        }
    }
}
