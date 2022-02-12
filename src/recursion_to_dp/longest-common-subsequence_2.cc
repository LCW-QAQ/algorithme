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
    dp.at(0).at(0) = a.at(0) == b.at(0) ? 1 : 0;
    for (int j = 1; j < blen; j++) {
      dp.at(0).at(j) = a.at(0) == b.at(j) ? 1 : dp.at(0).at(j - 1);
    }
    for (int i = 1; i < alen; i++) {
      dp.at(i).at(0) = a.at(i) == b.at(0) ? 1 : dp.at(i - 1).at(0);
    }
    for (int i = 1; i < alen; i++) {
      for (int j = 1; j < blen; j++) {
        int ans1 = dp.at(i).at(j - 1);
        int ans2 = dp.at(i - 1).at(j);
        int ans3 = a.at(i) == b.at(j) ? 1 + dp.at(i - 1).at(j - 1) : 0;
        dp.at(i).at(j) = max({ans1, ans2, ans3});
      }
    }
    return dp.at(alen - 1).at(blen - 1);
  }
};