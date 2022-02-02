#pragma once

#include <bits/stdc++.h>

using namespace std;

template <typename T>
class Node;

template <typename T>
class Edge;

template <typename T>
class Node {
 public:
  T val;
  // 有多少个节点指向当前节点
  int in;
  // 当前节点指向了多少个节点
  int out;
  // 当前节点所指向的节点
  vector<Node<T>*> nexts;
  // 当前节点指向的节点对应的边
  vector<Edge<T>> edges;

  Node(T& val) : val(val), in(0), out(0) {}

  Node(T&& val) : Node(val) {}

  bool operator==(const Node& rhs) const { return this == &rhs; }
};

template <typename T>
class Edge {
 public:
  Node<T>* from;
  Node<T>* to;
  int weight;

  Edge() : from(nullptr), to(nullptr), weight(0) {}

  Edge(Node<T>* from, Node<T>* to, int weight)
      : from(from), to(to), weight(weight) {}

  bool operator==(const Edge& rhs) const {
    return this->from == rhs.from && this->to == rhs.to;
  }

  struct edge_hash {
    size_t operator()(const Edge<T>& edge) const {
      return hash<void*>()((void*)&edge);
    }
  };
};

template <typename T>
struct edge_hash {
  size_t operator()(const Edge<T>& edge) const {
    return hash<void*>()((void*)&edge);
  }
};

template <typename T>
class Graph {
 public:
  // 一个图对应的所有节点
  unordered_map<T, Node<T>*> nodeMap;

  // 图对应的所有边
  unordered_set<Edge<T>, edge_hash<T>> edges;

  ~Graph() {
    for_each(nodeMap.begin(), nodeMap.end(), [](auto& entry) {
      delete entry.second;
      entry.second = nullptr;
    });
  }
};

Graph<int> createGraph(vector<vector<int>>& matrix) {
  Graph<int> graph;
  auto n = matrix.size();
  for (int i = 0; i < n; i++) {
    int weight = matrix[i][0];
    int from = matrix[i][1];
    int to = matrix[i][2];
    // 没有创建过的节点
    if (!graph.nodeMap.count(from)) {
      graph.nodeMap[from] = new Node(from);
    }
    if (!graph.nodeMap.count(to)) {
      graph.nodeMap[to] = new Node(to);
    }
    auto fromNode = graph.nodeMap[from];
    auto toNode = graph.nodeMap[to];
    auto edge = Edge{fromNode, toNode, weight};
    fromNode->nexts.push_back(toNode);
    fromNode->out++;
    toNode->in++;
    fromNode->edges.push_back(edge);
    graph.edges.insert(edge);
  }
  return graph;
}

class A {
 public:
  virtual void sayHello() { cout << "hello" << endl; }
  virtual void sayHello2() { cout << "hello2" << endl; }
};

/* int main() {
  cout << "hello" << endl;
  while (true) {
    vector<vector<int>> vec{
        {0, 1, 2},
        {0, 1, 4},
        {0, 2, 3},
    };
    auto graph = createGraph(vec);
  }
  return 0;
} */