#include <bits/stdc++.h>

#include "graph.hpp"

using namespace std;

class BinaryHeap {
 public:
  vector<Node<int>*> heap;

  // 记录每个节点在对堆中的位置
  unordered_map<Node<int>*, int> index_map;

  // 存储root到每个节点的距离
  unordered_map<Node<int>*, int> distance_map;

  BinaryHeap() = default;

  BinaryHeap(int n) : heap(n) {}

  /**
   * @brief push
   * 将节点加入小根堆中且更新距离
   */
  void push(Node<int>* node, int distance) {
    // 遇到过节点, 且没有决定距离
    if (index_map.count(node) && index_map[node] != -1) {
      // 看看当前节点走这条路会不会更近
      distance_map[node] = min(distance_map[node], distance);
      heap_push(node, index_map[node]);
    } else if (!index_map.count(node)) {  // 真的不在堆中就push进来
      heap.push_back(node);
      index_map[node] = heap.size() - 1;
      distance_map[node] = distance;
      heap_push(node, heap.size() - 1);
    }
  }

  /**
   * @brief 返回对最小的距离值
   *
   * @return pair<Node<int>*, int>
   */
  pair<Node<int>*, int> pop() {
    auto node = heap.at(0);
    auto distance = distance_map.at(node);
    pair res = {node, distance};
    swap(0, heap.size() - 1);
    // 当前节点指已经确定了
    index_map[node] = -1;
    // 结果返回, 距离表不需要在记录node信息
    distance_map.erase(node);
    // 弹出
    heap.pop_back();
    heapify(0, heap.size());
    return res;
  }

  /**
   * @brief 将index位置向下堆化
   */
  void heapify(int index, int size) {
    int left = left_of(index);
    while (left < size) {
      int smaller_child_idx =
          left + 1 < size && distance_map.at(heap.at(left + 1)) <
                                 distance_map.at(heap.at(left))
              ? left + 1
              : left;
      int smaller_idx = distance_map.at(heap.at(index)) <
                                distance_map.at(heap.at(smaller_child_idx))
                            ? index
                            : smaller_child_idx;
      if (smaller_idx == index) break;
      swap(smaller_idx, index);
      index = smaller_idx;
      left = left_of(index);
    }
  }

  /**
   * @brief 将index位置node节点向上堆化到正确的位置
   */
  void heap_push(Node<int>* node, int index) {
    while (distance_map.at(heap.at(index)) <
           distance_map.at(heap.at(parent_of(index)))) {
      swap(index, parent_of(index));
      index = parent_of(index);
    }
  }

  /**
   * @brief 交换堆中a, b两项
   */
  void swap(int a, int b) {
    index_map[heap.at(a)] = b;
    index_map[heap.at(b)] = a;
    std::swap(heap.at(a), heap.at(b));
  }

  int left_of(int index) { return index * 2 + 1; }

  int parent_of(int index) { return (index - 1) / 2; }

  bool empty() { return heap.empty(); }
};

/**
 * @brief dijkstra单元最短路径问题
 * 利用加强堆优化dijkstra搜索最小边的过程
 */
unordered_map<Node<int>*, int> dijkstra(Node<int>* node) {
  unordered_map<Node<int>*, int> ans;
  auto heap = BinaryHeap{};
  heap.push(node, 0);
  while (!heap.empty()) {
    auto [node, distance] = heap.pop();
    // 将节点所有边对应的节点加入堆
    for (auto& edge : node->edges) {
      heap.push(edge.to, edge.weight + distance);
    }
    // 设置ans
    ans[node] = distance;
  }
  return ans;
}