package com.example.morse.structure;

public class Node<T> {
    public T value;
    public Node<T> left;
    public Node<T> right;
    public Node<T> parent;

    public Node(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
