#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

/**
 * @brief dijkstra单元最短路径问题
 * 一般要求边是非负权重, 事实上dijkstra要求的是没有负的环
 * (a-->|2| b-->|-3| c-->|-2| a, 这个数据样本会无限循环, 每次转一圈都-3)
 */
unordered_map<Node<int>*, int> dijkstra(Node<int>* node) {
  // 存储node到其他节点的单元最短路径, 不存在表示无法到达理解为正无穷
  unordered_map<Node<int>*, int> distance_map;
  distance_map[node] = 0;  // 到self的距离是0
  // 记录选择的路径
  unordered_set<Node<int>*> node_set;
  // 获取没有确定距离的节点中最短路径节点
  auto min_node = get_min_distance_and_unselected_node(distance_map, node_set);
  while (min_node != nullptr) {
    int distance = distance_map[min_node];
    for (auto& edge : min_node->edges) {
      auto to_node = edge.to;
      // to_node没有处理过, 就直接设置距离
      if (!node_set.count(to_node)) {
        distance_map[to_node] = edge.weight + distance;
      } else {
        // to_node存在, 将距离设置为更短的
        distance_map[to_node] =
            min(distance_map[to_node], edge.weight + distance);
      }
    }
    // 当前节点的子节点处理处理完后, 当前节点的距离就确定了
    node_set.insert(min_node);
    min_node = get_min_distance_and_unselected_node(distance_map, node_set);
  }
  return distance_map;
}

/**
 * @brief Get the min distance and unselected node object
 * 在距离表中选择一个最小距离且没有确定距离的节点
 * 这里其实也是在贪心
 * 
 * @param distance_map 距离表
 * @param node_set 确定距离的节点
 * @return Node<int>* 
 */
Node<int>* get_min_distance_and_unselected_node(
    unordered_map<Node<int>*, int>& distance_map,
    unordered_set<Node<int>*>& node_set) {
  int min_distance = 0;
  Node<int>* min_node = nullptr;
  // 暴力迭代所有节点, 找到没有决定距离的最小节点, 时间复杂度O(N)很高
  for (auto& [node, distance] : distance_map) {
    // 没有确定距离 && 距离更小
    if (!node_set.count(node) && distance < min_distance) {
      min_node = node;
      min_distance = distance;
    }
  }
  return min_node;
}