#include <fstream>
#include <iostream>
#include <memory>

int main() {
  while (true) {
    std::fstream f("Main.kt");
    f.close();
  }
  return 0;
}