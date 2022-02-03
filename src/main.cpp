#include <bits/stdc++.h>

#include "union_find_set/union_find_set.hpp"

using namespace std;

int main() {
  default_random_engine dre;
  uniform_int_distribution<int> gen(100, 1000);
  int count = 0;
  while (true) {
    vector<int> vec;
    int iter_count = gen(dre);
    for (int i = 0; i < iter_count; i++) {
      vec.push_back(i);
    }
    UnionFindSet<int> ufs{vec};
    for (int i = 0; i < vec.size() - 1; i++) {
      ufs.union_(i, i + 1);
    }
    cout << "Round " << count++ << " complete!" << endl;
  }
}