package tree;

import java.util.HashMap;

public class TrieTree {
    static class Node {
        int pass;
        int end;
        HashMap<Integer, Node> nodes;

        public Node() {
            nodes = new HashMap<>();
        }
    }

    private Node root;

    public TrieTree() {
        root = new Node();
    }

    /**
     * 在前缀数中插入字符串
     *
     * @param word 字符串
     */
    public void insert(String word) {
        char[] chars = word.toCharArray();
        Node node = root;
        node.pass++;
        for (int code : chars) {
            node.nodes.putIfAbsent(code, new Node());
            node = node.nodes.get(code);
            node.pass++;
        }
        node.end++;
    }

    /**
     * 在前缀树种删除给定字符串
     *
     * @param word 字符串
     */
    public boolean remove(String word) {
        // 有这个字符串
        if (count(word) > 0) {
            char[] chars = word.toCharArray();
            Node node = root;
            node.pass--;
            for (int code : chars) {
                // 能进这个说明从这里开始以后的都只出现了一次, 就不许要再继续找了, 直接在nodes中删除, 后面的交给gc
                if (--node.nodes.get(code).pass == 0) {
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
     * 计算给定字符串在前缀树中出现了多少次
     *
     * @param word 字符串
     * @return word 出现次数
     */
    public int count(String word) {
        Node node = findNodeByWord(word);
        return node != null ? node.end : 0;
    }

    /**
     * 找到前缀数中有多少字符串以word作为前缀
     *
     * @param word 字符串
     * @return 以word为前缀的字符串
     */
    public int prefix(String word) {
        Node node = findNodeByWord(word);
        return node != null ? node.pass : 0;
    }

    /**
     * 给定字符串在前缀树中最后一个字符对应的node
     *
     * @param word 字符串
     * @return 给定字符串在前缀树中最后一个字符对应的node, 没有匹配的字符串返回null
     */
    public Node findNodeByWord(String word) {
        char[] chars = word.toCharArray();
        Node node = root;
        for (int code : chars) {
            if (!node.nodes.containsKey(code)) return null;
            node = node.nodes.get(code);
        }
        return node;
    }

    public static void main(String[] args) {
        TrieTree tt = new TrieTree();
        tt.insert("word");
        tt.insert("word");
        tt.insert("hello");
        tt.insert("will");
        tt.insert("wof");
        tt.insert("f");
        tt.insert("f");
        tt.insert("fee");
        tt.remove("fee");
        System.out.println(tt.count("word"));
        System.out.println(tt.prefix("w"));
        System.out.println(tt.prefix("wo"));
        System.out.println(tt.remove("word"));
        System.out.println(tt.count("word"));
        System.out.println(tt.remove("word"));
        System.out.println(tt.count("word"));
        System.out.println(tt.prefix("f"));
    }
}