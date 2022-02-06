#include <bits/stdc++.h>

using namespace std;

struct Node {
 public:
  int val;
  Node* left;
  Node* right;

  Node() : val(0), left(nullptr), right(nullptr) {}

  Node(int val, Node* left = nullptr, Node* right = nullptr)
      : val(val), left(left), right(right) {}
};

// 先序
void pre(Node* root) {
  stack<Node*> stk;
  stk.push(root);
  while (!stk.empty()) {
    auto node = stk.top();
    stk.pop();
    cout << node->val << endl;
    // 先序遍历时 head left right, 所以压栈的时候先right再压left
    if (node->right != nullptr) {
      stk.push(node->right);
    }
    if (node->left != nullptr) {
      stk.push(node->left);
    }
  }
}

// 中序
void mid(Node* root) {
  stack<Node*> stk;
  // 中序遍历是 left mid right, 所以先一直压栈到最左边
  Node* node = root;
  while (!stk.empty() || node != nullptr) {
    if (node != nullptr) {  // 一直压栈到最左边
      stk.push(node);
      node = node->left;
    } else {  // 左边没了, 处理mid
      node = stk.top();
      stk.pop();
      cout << node->val << endl;
      node = node->right;  // mid处理完了, 处理right
    }
  }
}

// 后序
void post1(Node* root) {
  // 思路很简单就是先序遍历的逆序, 所以将先序遍历结果压栈再弹栈就是后序遍历
  stack<Node*> stk, stk2;
  stk.push(root);
  while (!stk.empty()) {
    auto node = stk.top();
    stk.pop();
    stk2.push(node);
    // 注意在这里有两次压栈, 后续遍历是left right head, 其逆序是head right left
    if (node->left != nullptr) {
      stk.push(node->left);
    }
    if (node->right != nullptr) {
      stk.push(node->right);
    }
  }
  while (!stk2.empty()) {
    auto node = stk2.top();
    stk2.pop();
    cout << node->val << endl;
  }
}

// 使用一个栈完成后续遍历
void post2(Node* root) {
  stack<Node*> stk;
  stk.push(root);
  // 用来记录节点是否被遍历过
  Node* flag = nullptr;
  while (!stk.empty()) {
    auto node = stk.top();
    // left right head
    /*
    有left且left没有遍历过, 注意若flag == node->right意味着左边已经压过了,
    现在右边都压完了, 所以不许在处理当前节点了
    */
    if (node->left != nullptr && flag != node->left && flag != node->right) {
      stk.push(node->left);
    }
    // 有right且right没有被遍历过
    else if (node->right != nullptr && flag != node->right) {
      stk.push(node->right);
    } else {  // left right都处理完了, 现在处理mid
      cout << node->val << endl;
      flag = node;  // 记录当前节点被处理过
      stk.pop();
    }
  }
}

int main() {
  Node* root = new Node(0, new Node(1, new Node(3), new Node(4)),
                        new Node(2, new Node(5), new Node(6)));
  // pre(root);
  // mid(root);
  // post1(root);
  post2(root);
  return 0;
}