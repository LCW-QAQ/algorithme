#include <bits/stdc++.h>

using namespace std;

int process1(vector<int>& w, vector<int>& v, int bag, int index) {
  // 这里看题目具体规定, 这里假设不存在负数重量与价值
  if (bag < 0) {
    // 不能直接返回0, 我们需要返回-1表示不选择当前货物, 背包容量不够了
    return -1;
  }
  // 到最后一个位置了, 没有货物了, 价值当然是0
  if (index == w.size()) {
    return 0;
  }
  // 选择当前位置
  int ans1 = process1(w, v, bag, index + 1);
  // 不选择当前位置
  int ans2 = 0;
  // 选择当前位置后下一次的值
  int next = process1(w, v, bag - w.at(index), index + 1);
  // 非null判断
  if (next != -1) {
    ans2 = next + v.at(index);
  }
  return max(ans1, ans2);
}

/**
 * @brief 背包问题, 求优先背包容量内能存储的最大价值
 * 假设不存在负数重量与价值
 * @param w 货物重量
 * @param v 获取价值
 * @param bag 背包容量
 */
int max_value_by_violent_recursion(vector<int>& w, vector<int>& v, int bag) {
  return process1(w, v, bag, 0);
}

int max_value_by_dp(vector<int>& w, vector<int>& v, int bag) {
  vector<vector<int>> dp(w.size() + 1, vector<int>(bag + 1));
  int n = w.size();
  for (int index = n - 1; index >= 0; index--) {
    for (int rest = 0; rest <= bag; rest++) {
      int ans1 = dp.at(index + 1).at(rest);
      int ans2 = 0;
      int next =
          rest - w.at(index) < 0 ? -1 : dp.at(index + 1).at(rest - w.at(index));
      if (next != -1) {
        ans2 = v.at(index) + dp.at(index + 1).at(rest - w.at(index));
      }
      dp.at(index).at(rest) = max(ans1, ans2);
    }
  }
  return dp.at(0).at(bag);
}

int main() {
  vector<int> w{3, 2, 4, 7, 3, 1, 7}, v{5, 6, 3, 19, 12, 4, 2};
  int bag = 15;
  cout << max_value_by_violent_recursion(w, v, bag) << endl;
  cout << max_value_by_dp(w, v, bag) << endl;
}