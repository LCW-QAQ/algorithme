#include <bits/stdc++.h>

using namespace std;

/*
LFU 策略是什么?
优先比较访问次数, 其次比较访问时间
*/
struct Node {
  // k-v键值对 频率
  int key, val, freq;

  Node(int key, int val, int freq) : key(key), val(val), freq(freq) {}
};

class LFUCache {
 public:
  // 全局最小访问频次
  int min_freq;
  // 缓存大小
  int capacity;
  // 缓存表, 记录key与list中node节点迭代器
  unordered_map<int, list<Node>::iterator> cache_map;
  // 频次表, 记录指定频次的所有节点
  unordered_map<int, list<Node>> freq_map;

  LFUCache(int capacity) : capacity(capacity), min_freq(0) {}

  int get(int key) {
    if (capacity == 0) return -1;
    auto it = cache_map.find(key);
    // 没找到返回-1
    if (it == cache_map.end()) return -1;
    // 访问了频次需要变(增加频次)
    list<Node>::iterator node_it = it->second;
    int freq = node_it->freq, val = node_it->val;
    freq_map[freq].erase(node_it);
    // 频次list没有值就移除
    if (freq_map[freq].size() == 0) {
      freq_map.erase(freq);
      if (min_freq == freq) min_freq++;
    }
    freq_map[freq + 1].push_front(Node{key, val, freq + 1});
    cache_map[key] = freq_map[freq + 1].begin();
    return val;
  }

  void put(int key, int value) {
    if (capacity == 0) return;
    auto it = cache_map.find(key);
    // key不存在缓存中
    if (it == cache_map.end()) {
      // 缓存满了, 删除缓存中最小频次中时间最早的
      if (cache_map.size() == capacity) {
        auto rm_it = freq_map[min_freq].back();
        // 缓存表移除
        cache_map.erase(rm_it.key);
        // 频次表移除
        freq_map[min_freq].pop_back();
        // 频次表中的链表没有值了, 直接删除即可
        if (freq_map[min_freq].size() == 0) {
          freq_map.erase(min_freq);
        }
      }
      // 插入新值, 初始频次为1, 加入到频次表中
      freq_map[1].push_front(Node{key, value, 1});
      // 同时加入到cache_map中
      cache_map[key] = freq_map[1].begin();
      // 将最小频次置为1
      min_freq = 1;
    } else {
      list<Node>::iterator node_it = it->second;
      int freq = node_it->freq;
      // 频次会变化, 先将其在频次表中删除
      freq_map[freq].erase(node_it);
      // 没有节点了, 从频次表中删除list
      if (freq_map[freq].size() == 0) {
        freq_map.erase(freq);
        // 要删除的就是频次最小的list
        // 说明最小频次已经没有值了需要将频次++, 更新频次
        if (min_freq == freq) min_freq++;
      }
      // 更新节点并加入到链表最前面表示最近修改过
      freq_map[freq + 1].push_front(Node{key, value, freq + 1});
      // 更新缓存表中的值
      cache_map[key] = freq_map[freq + 1].begin();
    }
  }
};