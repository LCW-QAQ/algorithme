#include <bits/stdc++.h>

using namespace std;

/**
 * @brief 字符串全排列
 *
 * @param str 当前过程中还没有被选取的字符串
 * @param ans 答案
 * @param path 当前过程中的排列
 */
void process1(string str, vector<string>& ans, string path) {
  if (str.length() == 0) {
    ans.push_back(path);
    return;
  }
  for (int i = 0; i < str.length(); i++) {
    // 枚举当前过程str的每一个字符被选取的情况
    process1(str.substr(0, i) + str.substr(i + 1, str.size() - i - 1), ans,
             path + str.at(i));
  }
}

vector<string> permutation1(string str) {
  if (str.length() == 0) return {};
  vector<string> ans;
  process1(str, ans, "");
  return ans;
}

/**
 * @brief 字符串全排列
 *
 * @param str 给定字符串
 * @param ans 答案
 * @param index 当前过程中枚举到的字符的索引
 */
void process2(string& str, vector<string>& ans, int index) {
  // 枚举到最后一个了, 添加ans返回即可
  if (index == str.length()) {
    ans.push_back(str);
    return;
  }
  // 每次从index项开始枚举, index前面的都枚举过了
  for (int i = index; i < str.length(); i++) {
    // 第一次跟自己交换, 然后一次跟后面的字符交换
    swap(str.at(index), str.at(i));
    // 在index, i交换后, 求当前被枚举字符串, 剩下的排列即str[index + 1:]
    process2(str, ans, index + 1);
    // 枚举完成后, 需要将状态回滚
    swap(str.at(index), str.at(i));
  }
}

vector<string> permutation2(string str) {
  if (str.length() == 0) return {};
  vector<string> ans;
  process2(str, ans, 0);
  return ans;
}

void process3(string& str, vector<string>& ans, int index) {
  // 枚举到最后一个了, 添加ans返回即可
  if (index == str.length()) {
    ans.push_back(str);
    return;
  }
  // 表示i索引的字符是否在字符串出现过
  bool visited[256]{false};
  // 每次从index项开始枚举, index前面的都枚举过了
  for (int i = index; i < str.length(); i++) {
    if (!visited[str[i]]) {
      visited[str[i]] = true;
      // 第一次跟自己交换, 然后一次跟后面的字符交换
      swap(str.at(index), str.at(i));
      // 在index, i交换后, 求当前被枚举字符串, 剩下的排列即str[index + 1:]
      process3(str, ans, index + 1);
      // 枚举完成后, 需要将状态回滚
      swap(str.at(index), str.at(i));
    }
  }
}

vector<string> permutation3_distinct(string str) {
  if (str.length() == 0) return {};
  vector<string> ans;
  process3(str, ans, 0);
  return ans;
}

int main() {
  string str = "acc";
  auto ans1 = permutation1(str);
  for (auto& item : ans1) {
    cout << item << endl;
  }
  cout << "===========" << endl;
  auto ans2 = permutation2(str);
  for (auto& item : ans2) {
    cout << item << endl;
  }
  cout << "===========" << endl;
  auto ans3 = permutation3_distinct(str);
  for (auto& item : ans3) {
    cout << item << endl;
  }
  return 0;
}