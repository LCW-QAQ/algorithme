#include <bits/stdc++.h>

using namespace std;

/**
 * @brief 字符串左右子序列
 * 
 * @param str 给定字符串
 * @param index 从左向右一次枚举的过程中, 当前枚举位置的索引
 * @param ans 答案
 * @param path 子序列
 */
void process1(string& str, int index, vector<string>& ans, string path) {
  if (index == str.length()) {
    ans.push_back(path);
    return;
  }
  // 当index不存在时候的子序列
  process1(str, index + 1, ans, path);
  // 当index存在时候的子序列
  process1(str, index + 1, ans, path + str.at(index));
}

vector<string> all_str_subsequence(string str) {
  vector<string> ans;
  process1(str, 0, ans, "");
  return ans;
}

void process2(string& str, int index, unordered_set<string>& ans, string path) {
  if (index == str.length()) {
    ans.insert(path);
    return;
  }
  process2(str, index + 1, ans, path);
  process2(str, index + 1, ans, path + str.at(index));
}

/**
 * @brief 返回字符串左右子序列 去重
 * 
 * @param str 
 * @return unordered_set<string> 
 */
unordered_set<string> all_str_subsequence_distinct(string str) {
  unordered_set<string> ans;
  process2(str, 0, ans, "");
  return ans;
}

int main() {
  string str = "abcc";
  auto ans1 = all_str_subsequence(str);
  for (auto& item : ans1) {
    cout << item << endl;
  }
  cout << "===============" << endl;
  auto ans2 = all_str_subsequence_distinct(str);
  for (auto& item : ans2) {
    cout << item << endl;
  }
  return 0;
}