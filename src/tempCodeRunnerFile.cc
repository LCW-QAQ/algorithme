0; i < 10; i++) {
    heap.push(i);
  }
  while (!heap.empty()) {
    cout << heap.top() << endl;
    heap.pop();
  }