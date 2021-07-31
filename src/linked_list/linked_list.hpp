#pragma once

template<typename T>
class Node {
public:
    T value;
    Node* next;
    Node* prev;

    explicit Node(T value) : value(value), next(nullptr), prev(nullptr) {}
};

template<typename T>
class LinkedList {
private:
    Node<T>* head;
    Node<T>* tail;
    int size;

public:
    LinkedList() : head(nullptr), tail(nullptr), size(0) {};

    ~LinkedList() {
        if (head != nullptr) {
            Node<T>* begin = head;
            Node<T>* end = tail + 1;
            while (begin != end) {
                delete begin;
                begin++;
            }
        }
    }

    void push_back(const T& value) {
        Node<T>* newNode = new Node<T>(value);
        if (head == nullptr) {
            head = tail = newNode;
        } else {
            Node<T>* temp = tail;
            tail = newNode;
            temp->next = tail;
            tail->prev = temp;
        }
        size++;
    }

    void push_front(const T& value) {
        Node<T>* newNode = new Node<T>(value);
        if (head == nullptr) {
            head = tail = newNode;
        } else {
            Node<T>* temp = head;
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

    T& front() {
        return head->value;
    }

    T& back() {
        return tail->value;
    }
};