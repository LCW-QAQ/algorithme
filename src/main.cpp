#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

/**
 * @brief dijkstra单元最短路径问题
 * 一般要求边是非负权重, 事实上dijkstra要求的是没有负的环
 * (a-->|2| b-->|-3| c-->|-2| a, 这个数据样本会无限循环, 每次转一圈都-3)
 */
unordered_map<Node<int>*, int> dijkstra(Node<int>* node) {
  
}