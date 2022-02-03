#include <bits/stdc++.h>

template <typename T>
struct Node {
  T val;

  Node(T& val) : val(val) {}

  Node(T&& val) : Node(val) {}
};

template <typename T>
class UnionFindSet {
 public:
  std::unordered_map<T, Node<T>*> nodeMap;

  std::unordered_map<Node<T>*, Node<T>*> parentMap;

  std::unordered_map<Node<T>*, int> sizeMap;

  std::stack<Node<T>*> stk;

  size_t element_size;

  UnionFindSet(UnionFindSet& ufs) = delete;

  UnionFindSet(UnionFindSet&& ufs) = delete;

  UnionFindSet& operator=(UnionFindSet& ufs) = delete;

  UnionFindSet& operator=(UnionFindSet&& ufs) = delete;

  UnionFindSet(std::vector<T> vec) {
    for (auto& item : vec) {
      auto node = new Node(item);
      nodeMap[item] = node;
      // 默认的parent就是self
      parentMap[node] = node;
      sizeMap[node] = 1;
    }
    element_size = vec.size();
  }

  Node<T>* find(Node<T>* node) {
    // 寻找顶层父节点
    while (parentMap.at(node) != node) {
      stk.push(node);
      node = parentMap.at(node);
    }
    // 扁平化
    while (!stk.empty()) {
      parentMap[stk.top()] = node;
      stk.pop();
    }
    return node;
  }

  /**
   * @brief 判断a, b是否在同一个集合中, 请保证a, b存在于并查集
   */
  bool isSame(T a, T b) { return find(nodeMap.at(a)) == find(nodeMap.at(b)); }

  void union_(T a, T b) {
    auto aHead = find(nodeMap.at(a));
    auto bHead = find(nodeMap.at(b));
    // a, b不在一个集合, 我们才需要合并
    if (aHead != bHead) {
      auto aSize = sizeMap.at(aHead);
      auto bSize = sizeMap.at(bHead);
      auto smaller = aSize < bSize ? aHead : bHead;
      auto larger = smaller == aHead ? bHead : aHead;
      // 将更小的节点连接到更大的节点, 尽可能减少长链表
      parentMap[smaller] = larger;
      sizeMap[larger] = aSize + bSize;
      // 删除更小的头节点, 它现在已经不是头节点了. sizeMap只存储头节点信息
      sizeMap.erase(smaller);
      // 集合合并后, size减少
      element_size--;
    }
  }

  size_t size() { return element_size; }

  ~UnionFindSet() {
    for (auto& entry : nodeMap) {
      auto pNode = entry.second;
      delete pNode;
      pNode = nullptr;
    }
  }
};