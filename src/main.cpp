#include <cstring>
#include <ctime>
#include <fstream>
#include <iostream>
#include <memory>
#include <vector>

using namespace std;

class A {
 public:
  int a;
  char* s;
  int len;

  A() : a(0), s(nullptr), len(0) {}

  A(int a, char* s, int len) : a(a), s(s), len(len) {}

  A(A& other) { copy_func(other); }

  A(A&& other) { copy_func(other); }

  void copy_func(const A& other) {
    this->a = other.a;
    this->s = new char[other.len + 1];
    strcpy_s(this->s, other.len + 1, other.s);
  }

  void move_func(A& other) {
    this->a = other.a;
    this->s = other.s;
    other.a = 0;
    other.s = nullptr;
  }

  A& operator=(const A& other) {
    this->a = other.a;
    this->s = new char[other.len + 1];
    strcpy(this->s, other.s);
    return *this;
  }
};

constexpr int MAX_COUNT = 1000;

int main(int argc, char const* argv[]) {
  vector<A> vec;
  for (int i = 1; i < MAX_COUNT; i++) {
    vec.push_back(A(i, new char[i](), i));
  }
  cout << "对象创建完成" << endl;
  for (auto&& item : vec) {
    A a(item);
  }
  return 0;
}