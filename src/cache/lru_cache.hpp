#include <bits/stdc++.h>

template <typename K, typename V>
struct Node {
  K key;
  V value;
  Node* prev;
  Node* next;
  Node() : prev(nullptr), next(nullptr) {}
  Node(K key, V value) : key(key), value(value), prev(nullptr), next(nullptr) {}
};

template <typename K, typename V>
class LruCache {
 public:
  std::size_t capacity;
  Node<K, V>*head, *tail;
  std::unordered_map<K, Node<K, V>*> cache;

  LruCache(std::size_t capacity) : capacity(capacity) {
    head = new Node<K, V>();
    tail = new Node<K, V>();
    head->next = tail;
  }

  ~LruCache() {
    for (auto& [_, node] : cache) {
      delete node;
      node = nullptr;
    }
    delete head;
    delete tail;
    head = tail = nullptr;
  }

  void add_to_head(Node<K, V>* node) {
    node->next = head->next;
    head->next->prev = node;
    head->next = node;
    node->prev = head;
  }

  void remove_node(Node<K, V>* node) {
    node->prev->next = node->next;
    node->next->prev = node->prev;
  }

  Node<K, V>* remove_tail() {
    auto res = tail->prev;
    remove_node(res);
    return res;
  }

  void move_to_head(Node<K, V>* node) {
    remove_node(node);
    add_to_head(node);
  }

  V& get(K&& key) { return get(key); }

  V& get(K& key) {
    if (!cache.count(key)) {
      throw "has not key!";
    }
    auto node = cache[key];
    move_to_head(node);
    return node->value;
  }

  void insert(K&& key, V&& value) { insert(key, value); }

  void insert(K& key, V& value) {
    if (!cache.count(key)) {
      auto new_node = new Node(key, value);
      cache[key] = new_node;
      add_to_head(new_node);
      if (cache.size() > capacity) {
        auto tail = remove_tail();
        cache.erase(tail->key);
        delete tail;
      }
    } else {
      auto node = cache[key];
      node->value = value;
      move_to_head(node);
    }
  }
};

/* int main() {
  using namespace std;
  {
    LruCache<int, int> cache{4};
    for (int i = 0; i < 4; i++) {
      cache.insert(i, i);
    }
    cache.insert(99, 99);
    cache.insert(100, 100);
    cache.get(2);
    cache.insert(101, 101);
    for (auto& [key, node] : cache.cache) {
      cout << key << ":(" << node->key << ":" << node->value << ")" << endl;
    }
  }
  while (true) {
    LruCache<int, int> cache{50};
    for (int i = 0; i < 100; i++) {
      cache.insert(i, i);
    }
  }
  return 0;
} */