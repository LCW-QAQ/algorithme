#include <bits/stdc++.h>

using namespace std;

struct DirectedGraphNode {
  int label;
  vector<DirectedGraphNode*> neighbors;
  DirectedGraphNode(int x) : label(x){};
};

using Node = DirectedGraphNode;

class Solution {
 public:
  /**
   * @param graph: A list of Directed graph node
   * @return: Any topological order for the given graph.
   * 
   * 拓扑排序 -> dfs bfs
   * dfs 
   * x节点的子节点的数量 > y节点的子节点的数量, 那么x在拓扑排序中更考前
   */
  vector<DirectedGraphNode*> topSort(vector<DirectedGraphNode*> graph) {
    vector<Node*> ans;
    unordered_map<Node*, long long> m;
    // 计算图中每个节点的子节点数量
    for (auto& item : graph) {
      dfs(item, m);
    }
    vector<pair<Node*, long long>> records(m.size());
    // 拷贝m中的信息
    copy(m.begin(), m.end(), records.begin());
    // 降序records即可, 节点越大拓扑序越靠前
    sort(records.begin(), records.end(),
         [](auto& a, auto& b) { return a.second > b.second; });
    ans.resize(records.size());
    transform(records.begin(), records.end(), ans.begin(),
              [](auto& item) { return item.first; });
    return ans;
  }

  /**
   * @brief dfs图, 返回每个节点的子节点数量, 并存储到m表中
   * 返回long long lintcode给打数据太大了
   */
  long long dfs(Node* node, unordered_map<Node*, long long>& m) {
    if (m.count(node)) {
      return m[node];
    }
    // 默认为1, 表示当前节点
    long long count = 1;
    for (auto& item : node->neighbors) {
      count += dfs(item, m);
    }
    // 加上当前节点
    m[node] = count;
    // 返回的时候记得返回的应该是count + 1, 坑
    return count;
  }
};