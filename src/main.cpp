#include <bits/stdc++.h>

using namespace std;

int main() {
  vector<int> vec{1, 2, 34};
  swap(vec[0], vec[2]);
  for (auto& item : vec) {
    cout << item << " ";
  }
}