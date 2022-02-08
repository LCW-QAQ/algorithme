#include <bits/stdc++.h>

using namespace std;

/**
 * @brief 将栈底元素pop出来
 */
int pop_to_push_last(stack<int>& stk) {
  auto val = stk.top();
  stk.pop();
  if (stk.empty()) {
    // 栈空的时候直接返回
    return val;
  } else {
    // 继续pop
    auto last = pop_to_push_last(stk);
    // 将上一次的值push
    stk.push(val);
    // 返回pop出来的值, 因为base case是empty, 所以这个值将会是last(栈底元素)
    return last;
  }
}

void reverse_stack(stack<int>& stk) {
  if (stk.empty()) {
    return;
  }
  // 弹出最后一项
  int last = pop_to_push_last(stk);
  reverse_stack(stk);
  // 将最后一项push, 巧妙的利用了系统栈
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