#include <bits/stdc++.h>

using namespace std;

// https://leetcode-cn.com/problems/longest-common-subsequence/solution/
// 动态规划
class Solution {
 public:
  int longestCommonSubsequence(string a, string b) {
    const int alen = a.length(), blen = b.length();
    if (alen == 0 || blen == 0) {
      return 0;
    }
    vector<vector<int>> dp(alen + 1, vector<int>(blen + 1));
    for (int i = 1; i <= alen; i++) {
      for (int j = 1; j <= blen; j++) {
        // i,j有一个字符相等, 结果就是0..i-1, 0..j-1的值
        if (a[i - 1] == b[j - 1]) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {  // 看要i或要j那个公共子序列更大
          dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    return dp[alen][blen];
  }
};