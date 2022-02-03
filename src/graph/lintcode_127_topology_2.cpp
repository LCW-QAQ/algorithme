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
   * x节点的最大深度 > y节点的最大深度, 那么x在拓扑排序中 < y
   */
  vector<DirectedGraphNode*> topSort(vector<DirectedGraphNode*> graph) {
    vector<Node*> ans;
    unordered_map<Node*, long long> m;
    for (auto& item : graph) {
      dfs(item, m);
    }
    vector<pair<Node*, long long>> records(m.size());
    copy(m.begin(), m.end(), records.begin());
    sort(records.begin(), records.end(),
         [](auto& a, auto& b) { return a.second > b.second; });
    ans.resize(records.size());
    transform(records.begin(), records.end(), ans.begin(),
              [](auto& item) { return item.first; });
    return ans;
  }

  /**
   * @brief dfs图, 返回每个节点的最大深度, 并存储到m表中
   * 返回long long lintcode给打数据太大了
   */
  long long dfs(Node* node, unordered_map<Node*, long long>& m) {
    if (m.count(node)) {
      return m[node];
    }
    // 默认为1, 表示当前节点
    long long maxDeep = 1;
    for (auto& item : node->neighbors) {
      maxDeep = max(maxDeep, dfs(item, m));
    }
    // 虽然上面默认为1, 但是这里还是要+1, 想清楚这里计算的是当前节点深度与子节点的最大深度求max, 不包含当前节点, 所以还是要+1
    m[node] = maxDeep + 1;
    return maxDeep + 1;
  }
};