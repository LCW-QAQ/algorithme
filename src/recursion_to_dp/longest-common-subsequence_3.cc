#include <bits/stdc++.h>

using namespace std;

// https://leetcode-cn.com/problems/longest-common-subsequence/solution/
// 动态规划
class Solution {
 public:
  int longestCommonSubsequence(string a, string b) {
    int alen = a.length(), blen = b.length();
    if (alen == 0 || blen == 0) {
      return 0;
    }
    vector<vector<int>> dp(alen, vector<int>(blen));
    dp[0][0] = a[0] == b[0] ? 1 : 0;
    for (int j = 1; j < blen; j++) {
      dp[0][j] = a[0] == b[j] ? 1 : dp[0][j - 1];
    }
    for (int i = 1; i < alen; i++) {
      dp[i][0] = a[i] == b[0] ? 1 : dp[i - 1][0];
    }
    for (int i = 1; i < alen; i++) {
      for (int j = 1; j < blen; j++) {
        int ans1 = dp[i][j - 1];
        int ans2 = dp[i - 1][j];
        int ans3 = a[i] == b[j] ? 1 + dp[i - 1][j - 1] : 0;
        dp[i][j] = max({ans1, ans2, ans3});
      }
    }
    return dp[alen - 1][blen - 1];
  }
};