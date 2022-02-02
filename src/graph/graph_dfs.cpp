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
  cout << node->val << endl;
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
        cout << next->val << endl;
        // 这里直接break, 后续通过stack中的node再次回来处理其他子节点
        break;
      }
    }
  }
}

int main() {
  ;
  ;
}