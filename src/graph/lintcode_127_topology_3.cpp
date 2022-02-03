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
   * bfs
   * 记录每个节点in(即有多少个节点指向了它), in==0的即是当前拓扑排序的第一个
   */
  vector<DirectedGraphNode*> topSort(vector<DirectedGraphNode*> graph) {
    vector<Node*> ans;
    // 记录每个节点的in
    unordered_map<Node*, int> inMap;
    for (auto& node : graph) {
      // 遍历子节点, 自增子节点in即可
      for (auto& neighbor : node->neighbors) {
        inMap[neighbor] += 1;
      }
    }
    deque<Node*> q;
    // 将in为0的节点加入q, 即进入拓扑序
    for (auto& node : graph) {
      if (inMap[node] == 0) {
        q.push_back(node);
        ans.push_back(node);
      }
    }

    while (!q.empty()) {
      auto node = q.front();
      q.pop_front();
      for (auto& neighbor : node->neighbors) {
        // 在inMap中删除当前节点的影响, 即子节点`in - 1`, `in == 0`则表示是下一个拓扑序的第一个, 加入q
        if (--inMap[neighbor] == 0) {
          q.push_back(neighbor);
          ans.push_back(neighbor);
        }
      }
    }
    return ans;
  }
};