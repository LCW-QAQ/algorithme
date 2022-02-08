#include <bits/stdc++.h>

using namespace std;

/**
 * @brief 将栈底元素移动到栈顶
 */
int pop_to_push_last(stack<int>& stk) {
  auto val = stk.top();
  stk.pop();
  if (stk.empty()) {
    return val;
  } else {
    auto last = pop_to_push_last(stk);
    stk.push(val);
    return last;
  }
}

void reverse_stack(stack<int>& stk) {
  if (stk.empty()) {
    return;
  }
  int last = pop_to_push_last(stk);
  reverse_stack(stk);
  stk.push(last);
}

int main() {
  stack<int> stk;
  for (int i = 0; i < 10; i++) {
    stk.push(i);
  }
  reverse_stack(stk);
  while (!stk.empty()) {
    cout << stk.top() << endl;
    stk.pop();
  }
}