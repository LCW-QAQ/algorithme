#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

/**
 * @brief dijkstra单元最短路径问题
 * 一般要求边是非负权重, 事实上dijkstra要求的是没有负的环
 * (a-->|2| b-->|-3| c-->|-2| a, 这个数据样本会无限循环, 每次转一圈都-3)
 */
unordered_map<Node<int>*, int> dijkstra(Node<int>* node) {
  unordered_map<Node<int>*, int> distance_map;
  unordered_set<Node<int>*> node_set;
  distance_map[node] = 0;
  auto min_node = get_min_node(distance_map, node_set);
  while (min_node != nullptr) {
    int distance = distance_map[min_node];
    for (auto& edge : min_node->edges) {
      auto to_node = edge.to;
      if (!distance_map.count(to_node)) {
        distance_map[to_node] = distance;
      } else {
        distance_map[to_node] =
            min(distance_map[to_node], edge.weight + distance);
      }
    }
    node_set.insert(min_node);
    min_node = get_min_node(distance_map, node_set);
  }
  return distance_map;
}

Node<int>* get_min_node(unordered_map<Node<int>*, int>& distance_map,
                        unordered_set<Node<int>*>& node_set) {
  Node<int>* min_node = nullptr;
  int min_distance = 0;
  for (auto& [node, distance] : distance_map) {
    if (!node_set.count(node) && distance < min_distance) {
      min_node = node;
      min_distance = distance;
    }
  }
  return min_node;
}