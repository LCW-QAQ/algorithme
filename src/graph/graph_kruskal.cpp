#include <bits/stdc++.h>

#include "graph.hpp"
#include "union_find_set/union_find_set.hpp"

using namespace std;

struct EdgeGreater {
  bool operator()(const Edge<int>& a, const Edge<int>& b) const {
    return a.weight > b.weight;
  }
};

/**
 * @brief kruskal最小生成树
 * 加权连通图中连通所有节点且权重和最小
 * 思路:
 *  1. 将所有边加入按照边权重排序的小根堆 
 *     根据节点建立并查集
 *  2. 每次弹出最小的边, 最小的边对应的form, to节点
 *  3. from, to若不在同一个集合中就合并, 表示找到了最小生成树的一条边
 *     若from, to在同一个集合, 意味着之前合并过, 若再次合并就会形成环. 且由于是小根堆, 之前的边一定比当前边权重小. 因此不错操作
 *  4. 重复2, 3, 知道找到所有节点或者堆中没有值停止
 *
 * @param graph
 * @return vector<Edge<int>> 返回最小生成树的所有边
 */
vector<Edge<int>> kruskal(Graph<int> graph) {
  vector<Edge<int>> ans;
  // 存储所有节点
  vector<Node<int>*> nodes(graph.nodeMap.size());
  // 从graph中获取所有节点
  transform(graph.nodeMap.begin(), graph.nodeMap.end(), nodes.begin(),
            [](auto& item) { return item.second; });
  // 构建节点并查集
  UnionFindSet<Node<int>*> ufs(nodes);
  // 根据边的权重构建小根堆
  priority_queue<Edge<int>, vector<Edge<int>>, EdgeGreater> q;
  for (auto& edge : graph.edges) {
    q.push(edge);
  }
  while (!q.empty() || ans.size() == nodes.size()) {
    // 每次弹出权重最小的边
    auto edge = q.top();
    q.pop();
    // 若from到to不在同一集合内, 合并from, to
    if (!ufs.isSame(edge.from, edge.to)) {
      ans.push_back(edge);
      ufs.union_(edge.from, edge.to);
    }
  }
  return ans;
}