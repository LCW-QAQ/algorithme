#pragma once

template<typename _T>
class Node {
public:
    _T value;
    Node* next;
    Node* prev;

    explicit Node(_T value) : value(value), next(nullptr), prev(nullptr) {}
};

template<typename _T>
class LinkedList {
private:
    Node<_T>* head;
    Node<_T>* tail;
    int size;

public:
    LinkedList() : head(nullptr), tail(nullptr), size(0) {};

    ~LinkedList() {
        if (head != nullptr) {
            Node<_T>* begin = head;
            Node<_T>* end = tail + 1;
            while (begin != end) {
                delete begin;
                begin++;
            }
        }
    }

    void push_back(const _T& value) {
        Node<_T>* newNode = new Node<_T>(value);
        if (head == nullptr) {
            head = tail = newNode;
        } else {
            Node<_T>* temp = tail;
            tail = newNode;
            temp->next = tail;
            tail->prev = temp;
        }
        size++;
    }

    void push_front(const _T& value) {
        Node<_T>* newNode = new Node<_T>(value);
        if (head == nullptr) {
            head = tail = newNode;
        } else {
            Node<_T>* temp = head;
            head = newNode;
            temp->prev = newNode;
            head->next = newNode;
        }
        size++;
    }

    void pop_back() {
        if (head == nullptr) {
            return;
        } else if (head == tail) {
            delete head;
            head = tail = nullptr;
        } else {
            auto temp = tail;
            tail = tail->prev;
            tail->next = nullptr;
            delete temp;
        }
    }

    void pop_front() {
        if (head == nullptr) {
            return;
        } else if (head == tail) {
            delete head;
            head = tail = nullptr;
        } else {
            auto temp = head;
            head = head->next;
            head->prev = nullptr;
            delete temp;
        }
    }

    _T& front() {
        return head->value;
    }

    _T& back() {
        return tail->value;
    }
};