#include <bits/stdc++.h>

using namespace std;

int pre1(vector<int>& pokers, int l, int r);
int post1(vector<int>& pokers, int l, int r);

/**
 * @brief 暴力递归
 */
int win1(vector<int>& pokers) {
  if (pokers.size() == 0) return 0;
  int ans1 = pre1(pokers, 0, pokers.size() - 1);
  int ans2 = post1(pokers, 0, pokers.size() - 1);
  return max(ans1, ans2);
}

int pre1(vector<int>& pokers, int l, int r) {
  // 只有一个没得选
  if (l == r) {
    return pokers.at(l);
  }
  // 要么选左边, 要么选右边, 然后再后手选
  int ans1 = pokers.at(l) + post1(pokers, l + 1, r);
  int ans2 = pokers.at(r) + post1(pokers, l, r - 1);
  return max(ans1, ans2);
}

int post1(vector<int>& pokers, int l, int r) {
  // 因为是后手, 只有一张牌会被先手选择
  if (l == r) {
    return 0;
  }
  int ans1 = pre1(pokers, l + 1, r);
  int ans2 = pre1(pokers, l, r - 1);
  return min(ans1, ans2);
}

int pre2(vector<int>& pokers, int l, int r, vector<vector<int>>& pre_map,
         vector<vector<int>>& post_map);
int post2(vector<int>& pokers, int l, int r, vector<vector<int>>& pre_map,
          vector<vector<int>>& post_map);

/**
 * @brief 记忆化搜索
 */
int win2(vector<int>& pokers) {
  if (pokers.size() == 0) return 0;
  // pre_map记录先手在不同范围上的ans
  // post_map记录后手在不同范围上的ans
  vector<vector<int>> pre_map(pokers.size(), vector<int>(pokers.size(), -1)),
      post_map(pokers.size(), vector<int>(pokers.size(), -1));
  int ans1 = pre2(pokers, 0, pokers.size() - 1, pre_map, post_map);
  int ans2 = post2(pokers, 0, pokers.size() - 1, pre_map, post_map);
  return max(ans1, ans2);
}

int pre2(vector<int>& pokers, int l, int r, vector<vector<int>>& pre_map,
         vector<vector<int>>& post_map) {
  if (pre_map.at(l).at(r) != -1) {
    return pre_map.at(l).at(r);
  }
  int ans = 0;
  if (l == r) {
    ans = pokers.at(l);
  } else {
    int ans1 = pokers.at(l) + post2(pokers, l + 1, r, pre_map, post_map);
    int ans2 = pokers.at(r) + post2(pokers, l, r - 1, pre_map, post_map);
    ans = max(ans1, ans2);
  }
  pre_map.at(l).at(r) = ans;
  return ans;
}

int post2(vector<int>& pokers, int l, int r, vector<vector<int>>& pre_map,
          vector<vector<int>>& post_map) {
  if (post_map.at(l).at(r) != -1) {
    return post_map.at(l).at(r);
  }
  int ans = 0;
  if (l == r) {
    ans = 0;
  } else {
    int ans1 = pre2(pokers, l + 1, r, pre_map, post_map);
    int ans2 = pre2(pokers, l, r - 1, pre_map, post_map);
    ans = min(ans1, ans2);
  }
  post_map.at(l).at(r) = ans;
  return ans;
}

/**
 * @brief 动态规划填表
 * 根据暴力递归l == r base case:
 *    先手在l==r时, ans就是pokers[l]
 *    即pre_map中对角线的值为arr[i][i] = pokers[i]
 *
 *    后手在l==r时, ans为0, 即pre_map中对角线的值为0
 *
 *    普遍情况:
 *        先手的ans依赖于pokers[l] + 后手post_map(l + 1, r)
 *        与 pokers[r] + 后手post_map(l, r - 1)中的最大值
 *
 *        后手的ans依赖于先手pre_map(l + 1, r)
 *        与 pre_map(l, r - 1)的最小值(先手肯定会留下来更小的值)
 */
int win3(vector<int>& pokers) {
  if (pokers.size() == 0) return 0;
  const int n = pokers.size();
  vector<vector<int>> pre_map(n, vector<int>(n)), post_map(n, vector<int>(n));

  for (int i = 0; i < n; i++) {
    pre_map.at(i).at(i) = pokers.at(i);
  }
  for (int col = 1; col < n; col++) {
    int l = 0, r = col;
    while (r < n) {
      pre_map.at(l).at(r) = max(pokers.at(l) + post_map.at(l + 1).at(r),
                                pokers.at(r) + post_map.at(l).at(r - 1));
      post_map.at(l).at(r) =
          min(pre_map.at(l + 1).at(r), pre_map.at(l).at(r - 1));
      l++;
      r++;
    }
  }

  return max(pre_map.at(0).at(pokers.size() - 1),
             post_map.at(0).at(pokers.size() - 1));
}

/*
题目:
给定一个整型数组arr，代表数值不同的纸牌排成一条线
玩家A和玩家B依次拿走每张纸牌
规定玩家A先拿，玩家B后拿
但是每个玩家每次只能拿走最左或最右的纸牌
玩家A和玩家B都绝顶聪明
请返回最后获胜者的分数。
扑克牌为非负数
*/
int main() {
  vector<int> pokers{5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
  cout << win1(pokers) << endl;
  cout << win2(pokers) << endl;
  cout << win3(pokers) << endl;
  return 0;
}