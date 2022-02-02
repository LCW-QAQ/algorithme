#include "graph.hpp"

/**
 * @brief 图的深度优先遍历
 *
 * @param graph
 */
void dfs(Node<int>* node) {
  if (node == nullptr) return;
  stack<Node<int>*> stk;
  unordered_set<Node<int>*> sset;
  stk.push(node);
  sset.insert(node);
  cout << node->val << " ";
  while (!stk.empty()) {
    node = stk.top();
    stk.pop();
    // 遍历所有子节点
    for (auto& next : node->nexts) {
      // 只有没有遍历过的才需要处理
      if (!sset.count(next)) {
        // 当前节点再次入栈, 后续弹栈还要处理当前节点的其他子节点
        stk.push(node);
        // next入栈
        stk.push(next);
        // 记录next已经被处理过了
        sset.insert(next);
        cout << next->val << " ";
        // 这里直接break, 后续通过stack中的node再次回来处理其他子节点
        break;
      }
    }
  }
  cout << endl;
}

void dfs_recursion(Node<int>* node, unordered_set<Node<int>*>& sset) {
  if (node == nullptr) return;
  // 记录已经处理过当前node
  sset.insert(node);
  cout << node->val << " ";
  for (auto& next : node->nexts) {
    // 处理未处理过的子节点
    if (!sset.count(next)) {
      sset.insert(next);
      dfs_recursion(next, sset);
    }
  }
}

void dfs_recursion(Node<int>* node, unordered_set<Node<int>*>&& sset) {
  dfs_recursion(node, sset);
}

void bfs(Node<int>* node) {
  if (node == nullptr) return;
  deque<Node<int>*> q;
  unordered_set<Node<int>*> sset;
  q.push_back(node);
  sset.insert(node);
  while (!q.empty()) {
    node = q.front();
    q.pop_front();
    cout << node->val << " ";

    // 一次将邻居加入队列
    for (auto& next : node->nexts) {
      // 没处理过才需要入队列
      if (!sset.count(next)) {
        q.push_back(next);
        sset.insert(next);
      }
    }
  }
  cout << endl;
}

int main() {
  vector<vector<int>> vec{{0, 1, 2}, {0, 2, 3}, {0, 2, 4}, {0, 4, 1},
                          {0, 4, 5}, {0, 4, 6}, {0, 3, 7}};
  auto graph = createGraph(vec);
  auto node = graph.nodeMap[1];
  dfs(node);
  dfs_recursion(node, {});
  cout << endl;
  bfs(node);
}