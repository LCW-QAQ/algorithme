#include <bits/stdc++.h>

using namespace std;

// https://leetcode-cn.com/problems/longest-common-subsequence/solution/
// 暴力递归
class Solution {
 public:
  int longestCommonSubsequence(string text1, string text2) {
    if (text1.length() == 0 || text2.length() == 0) {
      return 0;
    }

    return process(text1, text2, text1.length() - 1, text2.length() - 1);
  }

  /**
   * a, b两个字符串分别从0到i,j有多少个最大公共子序列
   */
  int process(string& a, string& b, int i, int j) {
    // 都是第一个字符
    if (i == 0 && j == 0) {
      // 两个字符串的第一个字符相等就有一个公共子序列
      return a.at(i) == b.at(j) ? 1 : 0;
    } else if (i == 0) {         // i到头了, 只有一个字符
      if (a.at(i) == b.at(j)) {  // 判断i == j相等则有一个公共子序列
        return 1;
      } else {  // 不相等则缩小b的区间, 继续找
        return process(a, b, i, j - 1);
      }
    } else if (j == 0) {  // j到头了
      if (a.at(i) == b.at(j)) {
        return 1;
      } else {
        return process(a, b, i - 1, j);
      }
    } else {  // i != 0 && j != 0, 两个字符串都没到头
      // 公共子序列不以i结尾的情况
      int ans1 = process(a, b, i - 1, j);
      // 公共子序列不以j结尾的情况
      int ans2 = process(a, b, i, j - 1);
      // 公共子序列以i,j结尾的情况(i, j)
      int ans3 = a.at(i) == b.at(j) ? 1 + process(a, b, i - 1, j - 1) : 0;
      return max({ans1, ans2, ans3});
    }
  }
};