#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

struct EdgeGreater {
  bool operator()(const Edge<int>& a, const Edge<int>& b) const {
    return a.weight > b.weight;
  }
};

/**
 * @brief prim最小生成树
 * 思路:
 *  假设图中没有森林
 *  1. 建立按照边权重排序的小根堆
 *     建立用于记录节点是否处理过的集合
 *  2. 从任意节点出发
 *     没有处理过就记录到集合中, 解锁当前节点与之对应的边
 *  3. 将所有边加入小根堆
 *  4. 弹出最小权重边, 只要这条边对应的to节点没有处理过(即没有形成环)就重复2, 3,
 * 4, 并将当前边加入ans即可
 *
 * @param graph
 * @return vector<Edge<int>> 返回最小生成树的所有边
 */
vector<Edge<int>> prim(Graph<int> graph) {
  vector<Edge<int>> ans;
  // 记录节点是否处理过
  unordered_set<Node<int>*> nodeSet;
  // 一局Edge权重排序的小根堆
  priority_queue<Edge<int>, vector<Edge<int>>, EdgeGreater> q;
  // 所有节点
  vector<Node<int>*> nodes(graph.nodeMap.size());
  transform(graph.nodeMap.begin(), graph.nodeMap.end(), nodes.begin(),
            [](auto& item) { return item.second; });
  // 遍历所有节点, 如果题目给出的图不会出现森林, 那么久不用遍历了,
  // 随机拿出一个节点即可
  for (auto& node : nodes) {
    // 如果node不存在nodeSet中就进行处理
    if (!nodeSet.count(node)) {
      nodeSet.insert(node);
      // 将当前节点所有的边加入按Edge权重排序的小根堆中
      for (auto& edge : node->edges) {
        q.push(edge);
      }
      while (!q.empty()) {
        // 弹出了权重最小的边
        auto edge = q.top();
        q.pop();
        auto node = edge.to;
        // 接下来要处理的节点, 没有处理过才需要处理(是否形成环,
        // 没有形成环才需要处理)
        if (!nodeSet.count(node)) {
          nodeSet.insert(node);
          // 没有形成环切是局部最小权重, 那么他就是答案
          ans.push_back(edge);
          // 继续加入新节点的所有边
          for (auto& nextEdge : node->edges) {
            q.push(nextEdge);
          }
        }
      }
    }
    // 是否break也取决于图是否出现森林
    break;
  }
  return ans;
}