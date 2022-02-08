#include <bits/stdc++.h>

template <typename T, typename _Cmp = std::less<T>>
class BinaryHeap {
 private:
  std::vector<T> heap;

  void push_heap(std::size_t index) {
    while (_Cmp(heap.at(index), heap.at(parent_of(index)))) {
      std::swap(heap.at(index), heap.at(parent_of(index)));
      index = parent_of(index);
    }
  }

  void heapify(std::size_t index, std::size_t size) {
    auto left = left_of(index);
    while (left < size) {
      auto smaller_child_idx =
          left + 1 < size && _Cmp(heap.at(left + 1), heap.at(left)) ? left + 1
                                                                    : left;
      auto smaller_idx = _Cmp(heap.at(smaller_child_idx), heap.at(index))
                             ? smaller_child_idx
                             : index;
      if (smaller_idx == index) break;
      std::swap(heap.at(smaller_idx), heap.at(index));
      index = smaller_idx;
      left = left_of(index);
    }
  }

  std::size_t left_of(std::size_t index) { return index * 2 + 1; }

  std::size_t parent_of(std::size_t index) { return (index - 1) / 2; }

 public:
  void push(T& t) {
    heap.push_back(t);
    push_heap(heap.size() - 1);
  }

  void push(T&& t) { return push(t); }

  T top() { return heap.at(0); }

  void pop() {
    std::swap(heap.at(0), heap.at(heap.size() - 1));
    heap.pop_back();
    heapify(0, heap.size());
  }

  bool empty() { return heap.empty(); }

  std::size_t size() { return heap.size(); }
};