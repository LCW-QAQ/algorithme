#include <bits/stdc++.h>

using namespace std;

struct Node {
  // k-v键值对
  int key, val;
  // 访问次数 最近更新时间
  int cnt, time;

  Node(int key, int val, int cnt, int time)
      : key(key), val(val), cnt(cnt), time(time) {}

  // 排序策略是LFU的关键, LFU相比LRU粒度更细
  bool operator<(const Node& rhs) const {
    // 优先比较访问次数
    // 若访问次数相同则比较最近访问的时间(访问时间早的缓存失效)
    return cnt == rhs.cnt ? time < rhs.time : cnt < rhs.cnt;
  }
};

class LFUCache {
 public:
  unordered_map<int, Node> cache;
  set<Node> sset;
  // 缓存容量 当前操作时间
  int capacity, time;

  LFUCache(int capacity) : capacity(capacity), time(0) {}

  int get(int key) {
    // 容量为0且没有key返回-1
    if (capacity == 0 || !cache.count(key)) return -1;
    // 记得使用find, 当value没有无参构造的时候, []运算符会出错
    auto& node = cache.find(key)->second;
    sset.erase(node);
    // 更新访问次数与时间
    node.cnt++;
    node.time = ++time;
    sset.insert(node);
    return node.val;
  }

  void put(int key, int value) {
    // 缓存没有容量当然就直接返回
    if (capacity == 0) return;
    if (!cache.count(key)) {
      // 请先删除缓存 再新建节点
      if (cache.size() >= capacity) {
        // 在缓存中删除即将过期的node
        cache.erase(sset.begin()->key);
        sset.erase(sset.begin());
      }
      auto node = Node(key, value, 1, ++time);
      // cache[key] = node;
      cache.insert({key, node});
      sset.insert(node);
    } else {  // 缓存中已经有了
      // 记得使用find, 当value没有无参构造的时候, []运算符会出错
      auto& node = cache.find(key)->second;
      // 在红黑树中移除
      sset.erase(node);
      node.cnt++;
      node.time = ++time;
      node.val = value;
      sset.insert(node);
    }
  }
};

int main() {
  ;
  ;
}