#include <bits/stdc++.h>

using namespace std;

/*
https://leetcode-cn.com/problems/stickers-to-spell-word/
*/
// 暴力递归, 未剪枝, 超时
class Solution {
 public:
  int minStickers(vector<string>& stickers, string target) {
    int ans = process(stickers, target);
    return ans == INT32_MAX ? -1 : ans;
  }

  int process(vector<string>& stickers, string target) {
    if (target.length() == 0) {
      return 0;
    }
    int min = INT32_MAX;
    for (auto& sticker : stickers) {
      // 枚举所有贴纸, target剪去贴纸后的结果
      string rest = minus(sticker, target);
      if (rest.length() != target.length()) {
        min = std::min(min, process(stickers, rest));
      }
    }
    return min + (min == INT32_MAX ? 0 : 1);
  }

  string minus(string& sticker, string target) {
    int cnt[26]{};
    for (auto c : target) {
      cnt[c - 'a']++;
    }
    for (auto c : sticker) {
      cnt[c - 'a']--;
    }
    string res;
    for (int i = 0; i < 26; i++) {
      for (int j = 0; j < cnt[i]; j++) {
        res += 'a' + i;
      }
    }
    return res;
  }
};

int main() { return 0; }