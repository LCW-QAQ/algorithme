#include <bits/stdc++.h>

using namespace std;

/*
https://leetcode-cn.com/problems/stickers-to-spell-word/
*/
// 暴力递归, 优化 + 剪枝
constexpr int LETTER_NUM = 26;
class Solution {
 public:
  int minStickers(vector<string>& stickers, string target) {
    vector<array<int, LETTER_NUM>> stickers_(stickers.size());
    // 将字符串全部替换成词频统计的方式, 效率更高
    for (auto& sticker : stickers) {
      array<int, LETTER_NUM> cnt{};
      for (auto c : sticker) {
        cnt.at(c - 'a')++;
      }
      stickers_.push_back(cnt);
    }
    int ans = process(stickers_, target);
    return ans == INT32_MAX ? -1 : ans;
  }

  int process(vector<array<int, LETTER_NUM>>& stickers, string target) {
    if (target.length() == 0) {
      return 0;
    }
    array<int, LETTER_NUM> target_cnt{};
    // target词频统计
    for (auto c : target) {
      target_cnt.at(c - 'a')++;
    }
    int min = INT32_MAX;
    for (auto& sticker : stickers) {
      // 剪枝, 必要
      // 只有在贴纸真的可以剪贴的时候才继续
      if (sticker.at(target.at(0) - 'a') > 0) {
        string rest;
        for (int i = 0; i < LETTER_NUM; i++) {
          // 根据词频统计差, 追加字符串
          int num = target_cnt.at(i) - sticker.at(i);
          for (int j = 0; j < num; j++) {
            rest += i + 'a';
          }
        }
        min = std::min(min, process(stickers, rest));
      }
    }
    return min + (min == INT32_MAX ? 0 : 1);
  }
};

int main() { return 0; }