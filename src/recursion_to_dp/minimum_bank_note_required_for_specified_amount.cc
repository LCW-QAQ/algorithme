#include <bits/stdc++.h>

using namespace std;

constexpr int MAX_LEN = 1000;

array<int, MAX_LEN> dp{};

int main() {
  // 假设钞票有面值1, 5, 11
  // 求凑出n元钱, 最少需要多少张钞票
  int n;
  cin >> n;
  // dp[i]表示凑出i元钱, 需要最少多少张钞票
  // dp[0]默认为0
  /*
  dp[15]的ans依赖于三种情况:
      分别使用一张1, 5, 11面值的钞票后的ans + 1(当前用掉的这张钞票)
  */
  for (int i = 1; i <= n; i++) {
    int cost = INT32_MAX;
    if (i - 1 >= 0) {
      cost = min(cost, dp.at(i - 1) + 1);
    }
    if (i - 5 >= 0) {
      cost = min(cost, dp.at(i - 5) + 1);
    }
    if (i - 11 >= 0) {
      cost = min(cost, dp.at(i - 11) + 1);
    }
    dp.at(i) = cost;
    cout << "dp[" << i << "]"
         << "=" << dp.at(i) << endl;
  }
  cout << dp[n] << endl;
}