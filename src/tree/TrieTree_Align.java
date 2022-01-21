package tree;

import java.util.HashMap;

/**
 * @author liuchongwei
 * @email lcwliuchongwei@qq.com
 * @date 2022-01-21
 */
public class TrieTree_Align {
    static class Node {
        int pass;
        int end;
        HashMap<Integer, Node> nodes = new HashMap<>();
    }

    private Node root;

    public TrieTree_Align() {
        root = new Node();
    }

    public void insert(String str) {
        final char[] chars = str.toCharArray();
        Node node = root;
        // 首先肯定经过根节点
        node.pass++;
        for (int code : chars) {
            // 建立每个字符的路径
            node.nodes.putIfAbsent(code, new Node());
            node = node.nodes.get(code);
            // 记录经过子字符
            node.pass++;
        }
        node.end++;
    }

    /**
     * 在前缀树中删除指定字符串
     */
    public boolean remove(String str) {
        if (count(str) > 0) {
            final char[] chars = str.toCharArray();
            Node node = root;
            node.pass--;
            for (int code : chars) {
                final Node subNode = node.nodes.get(code);
                subNode.pass--;
                // 没有字符串经过, 就删除, 防止内存泄漏
                if (subNode.pass == 0) {
                    node.nodes.remove(code);
                    return true;
                }
                node = node.nodes.get(code);
            }
            node.end--;
            return true;
        }
        return false;
    }

    /**
     * 获取指定字符串, 在前缀树中出现了多少次
     */
    public int count(String str) {
        Node node = findNodeByWord(str);
        return node != null ? node.end : 0;
    }

    /**
     * 获取前缀树中有多少个字符串以str作为前缀
     */
    public int prefix(String str) {
        Node node = findNodeByWord(str);
        return node != null ? node.pass : 0;
    }

    /**
     * 获取指定字符串结尾字符在前准树中的node节点
     */
    private Node findNodeByWord(String str) {
        final char[] chars = str.toCharArray();
        Node node = root;
        for (int code : chars) {
            if (!node.nodes.containsKey(code)) return null;
            node = node.nodes.get(code);
        }
        return node;
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            TrieTree trie1 = new TrieTree();
            TrieTree trie2 = new TrieTree();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.remove(arr[j]);
                    trie2.remove(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.count(arr[j]);
                    int ans2 = trie2.count(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefix(arr[j]);
                    int ans2 = trie2.prefix(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
