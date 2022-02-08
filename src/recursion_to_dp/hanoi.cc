#include <bits/stdc++.h>

using namespace std;

void hanoi1_left_to_right(int n);
void hanoi1_left_to_mid(int n);
void hanoi1_mid_to_right(int n);
void hanoi1_right_to_left(int n);
void hanoi1_mid_to_left(int n);
void hanoi1_right_to_mid(int n);

void hanoi1(int n) {
  if (n > 0) {
    hanoi1_left_to_right(n);
  }
}

void hanoi1_left_to_right(int n) {
  if (n == 1) {
    cout << "move " << n << " from left to right" << endl;
    return;
  }
  hanoi1_left_to_mid(n - 1);
  cout << "move " << n << " from left to right" << endl;
  hanoi1_mid_to_right(n - 1);
}

void hanoi1_left_to_mid(int n) {
  if (n == 1) {
    cout << "move " << n << " from left to mid" << endl;
    return;
  }
  hanoi1_left_to_right(n - 1);
  cout << "move " << n << " from left to mid" << endl;
  hanoi1_right_to_left(n - 1);
}

void hanoi1_mid_to_right(int n) {
  if (n == 1) {
    cout << "move " << n << " from mid to right" << endl;
    return;
  }
  hanoi1_mid_to_left(n - 1);
  cout << "move " << n << " from mid to right" << endl;
  hanoi1_left_to_right(n - 1);
}

void hanoi1_right_to_left(int n) {
  if (n == 1) {
    cout << "move " << n << " from right to left" << endl;
    return;
  }
  hanoi1_right_to_mid(n - 1);
  cout << "move " << n << " from right to left" << endl;
  hanoi1_mid_to_left(n - 1);
}

void hanoi1_mid_to_left(int n) {
  if (n == 1) {
    cout << "move " << n << " from mid to left" << endl;
    return;
  }
  hanoi1_mid_to_right(n - 1);
  cout << "move " << n << " from mid to left" << endl;
  hanoi1_right_to_left(n - 1);
}

void hanoi1_right_to_mid(int n) {
  if (n == 1) {
    cout << "move " << n << " from right to mid" << endl;
    return;
  }
  hanoi1_right_to_left(n - 1);
  cout << "move " << n << " from right to mid" << endl;
  hanoi1_left_to_mid(n - 1);
}

void hanoi2(int n, string& from, string& to, string& other) {
  assert(n > 0);
  if (n == 1) {
    cout << "move " << n << " from " << from << " to " << to << endl;
    return;
  }
  hanoi2(n - 1, from, other, to);
  cout << "move " << n << " from " << from << " to " << to << endl;
  hanoi2(n - 1, other, to, from);
}

void hanoi2(int n, string&& from, string&& to, string&& other) {
  hanoi2(n, from, to, other);
}

struct Info {
  bool finish;
  int n;
  string from;
  string to;
  string other;

  Info(bool finish, int n, string left, string mid, string right)
      : finish(finish), n(n), from(left), to(mid), other(right) {}
};

void hanoi3(int n) {
  if (n < 1) return;
  stack<Info> stk;
  stk.push(Info{false, 3, "left", "right", "mid"});
  while (!stk.empty()) {
    auto info = stk.top();
    stk.pop();
    if (info.n == 1) {
      cout << "move " << info.n << " from " << info.from << " to " << info.to
           << endl;
      if (!stk.empty()) {
        stk.top().finish = true;
      }
    } else {
      if (!info.finish) {
        stk.push(info);
        stk.push(Info{false, info.n - 1, info.from, info.other, info.to});
      } else {
        cout << "move " << info.n << " from " << info.from << " to " << info.to
             << endl;
        stk.push(Info{false, info.n - 1, info.other, info.to, info.from});
      }
    }
  }
}

int main() {
  hanoi1(3);
  cout << "==========" << endl;
  hanoi2(3, "left", "right", "mid");
  cout << "==========" << endl;
  hanoi3(3);
}