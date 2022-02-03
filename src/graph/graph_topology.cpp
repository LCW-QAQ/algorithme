#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

vector<Node<int>*> sortedTopology(Graph<int> graph) {
  vector<Node<int>*> ans;
  deque<Node<int>*> zeroQ;
  // 记录节点的剩余in, 后续会对in--
  unordered_map<Node<int>*, int> inMap;
  for (auto& [val, node] : graph.nodeMap) {
    // 记录node的in值
    inMap[node] = node->in;
    // 说明是当前拓扑序开始节点
    if (node->in == 0) {
      zeroQ.push_back(node);
    }
  }
  while (!zeroQ.empty()) {
    // node就是in为0的也就是当前拓扑序的第一个节点
    auto node = zeroQ.front();
    zeroQ.pop_front();
    // 存入ans
    ans.push_back(node);
    for (auto& next : node->nexts) {
      // 遍历子节点, 不需要用set去重, 拓扑排序要求无环
      /*
       如果子节点的`in - 1`(也就是减去当前节点的值)后为0,
       那么他就是下一轮拓扑序的第一个
       */
      if (--inMap[next] == 0) {
        zeroQ.push_back(next);
      }
    }
  }
  return ans;
}