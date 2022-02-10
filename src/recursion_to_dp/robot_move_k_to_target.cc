#include <bits/stdc++.h>

using namespace std;

/**
 * 机器人在[1, n]范围上, 从cur位置移动到aim位置, 还剩有rest步要走, 有多少种方法
 * 暴力递归
 */
int process1(int n, int aim, int cur, int rest) {
  // 没有步数了, 走到指定位置返回1
  if (rest == 0) {
    return cur == aim ? 1 : 0;
  }
  // 走到左边界后, 只能往右边走
  if (cur == 1) {
    return process1(n, aim, 2, rest - 1);
  }
  // 走到右边界后, 只能往左边走
  if (cur == n) {
    return process1(n, aim, n - 1, rest - 1);
  }
  // 往左右都可以走
  return process1(n, aim, cur - 1, rest - 1) +
         process1(n, aim, cur + 1, rest - 1);
}

/**
 * @brief 机器人在[1, n]范围上, 从start位置移动到aim位置, 必须走k步有多少中方法
 *
 * 暴力递归
 */
int robot_move_k_to_target_by_violent_recursion(int n, int aim, int start,
                                                int k) {
  return process1(n, aim, start, k);
}

int process2(int n, int aim, int cur, int rest, vector<vector<int>>& dp) {
  if (dp.at(cur).at(rest) != -1) {
    return dp.at(cur).at(rest);
  }
  int ans = 0;
  if (rest == 0) {
    ans += cur == aim ? 1 : 0;
  } else if (cur == 1) {
    ans += process2(n, aim, 2, rest - 1, dp);
  } else if (cur == n) {
    ans += process2(n, aim, n - 1, rest - 1, dp);
  } else {
    ans += process2(n, aim, cur - 1, rest - 1, dp) +
           process2(n, aim, cur + 1, rest - 1, dp);
  }
  dp.at(cur).at(rest) = ans;
  return ans;
}

/**
 * 记忆化搜索, 利用动态规划思想记录重复计算子过程
 */
int robot_move_k_to_target_by_memory_search(int n, int aim, int start, int k) {
  // row表示当前位置, col表示可以走多少步
  vector<vector<int>> dp(n + 1, vector<int>(k + 1, -1));
  return process2(n, aim, start, k, dp);
}

/**
 * dp表的row表示当前位置, col表示可以走多少步
 * 当剩余步数rest == 0时, 只有aim位置满足条件
 * 当剩余步数start == 1时, 2行的rest-1列才会影响结果
 * 当剩余步数start == n时, n-1行的rest-1列才会影响结果
 * 对普遍位置结果为: cur - 1行的rest - 1列 + cur + 1行的rest - 1列
 */
int robot_move_k_to_target_by_dp(int n, int aim, int start, int k) {
  vector<vector<int>> dp(n + 1, vector<int>(k + 1));
  dp.at(aim).at(0) = 1;
  for (int rest = 1; rest <= k; rest++) {
    dp.at(1).at(rest) = dp.at(2).at(rest - 1);
    for (int cur = 2; cur < n; cur++) {
      dp.at(cur).at(rest) =
          dp.at(cur - 1).at(rest - 1) + dp.at(cur + 1).at(rest - 1);
    }
    dp.at(n).at(rest) = dp.at(n - 1).at(rest - 1);
  }
  return dp.at(start).at(k);
}

int main() {
  cout << robot_move_k_to_target_by_violent_recursion(5, 4, 2, 6) << endl;
  cout << robot_move_k_to_target_by_memory_search(5, 4, 2, 6) << endl;
  cout << robot_move_k_to_target_by_dp(5, 4, 2, 6) << endl;
}