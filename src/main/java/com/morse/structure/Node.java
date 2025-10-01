package com.morse.structure;

import javafx.scene.shape.Circle;

public class Node<T> {

    public T value;
    public Node<T> left;
    public Node<T> right;

    public Circle circle;

    public Node(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.circle = null;
    }
}
