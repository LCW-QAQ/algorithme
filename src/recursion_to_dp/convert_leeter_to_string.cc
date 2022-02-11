#include <bits/stdc++.h>

using namespace std;

int process1(string& str, int index) {
  // 转换结束返回, 获得一种正确的转换策略
  if (index == str.size()) {
    return 1;
  }
  // 面对'0'开头, 无法转换, 说明之前的转换策略是错的
  if (str.at(index) == '0') {
    return 0;
  }
  // 当前字符单独成一个字母
  int ans = process1(str, index + 1);
  // 还有下一个字符, 且下一个字符是 < 数字27的有效字符, 那么就考虑合并两个字符
  if (index + 1 < str.size() &&
      (str.at(index) - '0') * 10 + (str.at(index + 1) - '0') < 27) {
    ans += process1(str, index + 2);
  }
  return ans;
}

/**
 * str中只包含字符'0' - '9'
 * '1' - '26'表示'A' - 'Z'
 *
 * str = "111"
 * 可以将每个'1'单独看做'A', 结果: 'AAA'
 * 可以将前面或后面的连续两个'1'看做k, 结果: 'AK' 或 'KA'
 * 请问字符串中有多少种转换策略
 */
int ways_by_violent_recursion(string& str) {
  if (str.size() == 0 || str == "") return 0;
  return process1(str, 0);
}

int ways_by_dp(string& str) {
  if (str.size() == 0 || str == "") return 0;
  int n = str.size();
  vector<int> dp(n + 1);
  dp.at(n) = 1;
  for (int i = n - 1; i >= 0; i--) {
    if (str.at(i) != '0') {
      int ans = dp.at(i + 1);
      if (i + 1 < n && (str.at(i) - '0') * 10 + (str.at(i + 1) - '0') < 27) {
        ans += dp.at(i + 2);
      }
      dp.at(i) = ans;
    }
  }
  return dp.at(0);
}

int main() {
  string str;
  for (int i = 0; i < 40; i++) {
    srand(time(0));
    if (rand() % 2 == 0) {
      str += "2";
    } else {
      str += "1";
    }
  }
  auto start = chrono::steady_clock::now();
  cout << ways_by_violent_recursion(str) << endl;
  cout << "ways_by_violent_recursion: "
       << chrono::duration_cast<chrono::milliseconds>(
              chrono::steady_clock::now() - start)
              .count()
       << endl;
  start = chrono::steady_clock::now();
  cout << ways_by_dp(str) << endl;
  cout << "ways_by_dp: "
       << chrono::duration_cast<chrono::milliseconds>(
              chrono::steady_clock::now() - start)
              .count()
       << endl;
}