#include <bits/stdc++.h>

template <typename T, typename _Cmp = std::less<T>>
class BinaryHeap {
 private:
  std::vector<T> heap;

  std::unordered_map<T, std::size_t> index_map;

  _Cmp value_cmp;

  void push_heap(std::size_t index) {
    while (value_cmp(heap.at(index), heap.at(parent_of(index)))) {
      swap(index, parent_of(index));
      index = parent_of(index);
    }
  }

  void heapify(std::size_t index, std::size_t size) {
    auto left = left_of(index);
    while (left < size) {
      auto smaller_child_idx =
          left + 1 < size && value_cmp(heap.at(left + 1), heap.at(left))
              ? left + 1
              : left;
      auto smaller_idx = value_cmp(heap.at(smaller_child_idx), heap.at(index))
                             ? smaller_child_idx
                             : index;
      if (smaller_idx == index) break;
      swap(index, smaller_idx);
      index = smaller_idx;
      left = left_of(index);
    }
  }

  void swap(int a, int b) {
    index_map[heap.at(a)] = b;
    index_map[heap.at(b)] = a;
    std::swap(heap.at(a), heap.at(b));
  }

  std::size_t left_of(std::size_t index) { return index * 2 + 1; }

  std::size_t parent_of(std::size_t index) {
    return index == 0 ? 0 : (index - 1) / 2;
  }

 public:
  BinaryHeap() = default;

  void push(T& t) {
    heap.push_back(t);
    index_map[t] = heap.size() - 1;
    push_heap(heap.size() - 1);
  }

  void push(T&& t) { return push(t); }

  T top() { return heap.at(0); }

  void pop() {
    index_map.erase(heap.at(0));
    swap(0, heap.size() - 1);
    heap.pop_back();
    heapify(0, heap.size());
  }

  void remove(T t) {
    auto rm_idx = index_map.at(t);
    index_map.erase(t);
    auto last_item = heap.at(heap.size() - 1);
    heap.pop_back();
    if (rm_idx != heap.size() - 1) {
      heap.at(rm_idx) = last_item;
      index_map[last_item] = rm_idx;
      resign(rm_idx);
    }
  }

  void resign(std::size_t index) {
    push_heap(index);
    heapify(index, heap.size());
  }

  void resign(T t) {
    resign(index_map.at(t));
  }

  bool empty() { return heap.empty(); }

  std::size_t size() { return heap.size(); }
};