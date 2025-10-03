package com.morse.structure;

import javafx.scene.paint.Color;

public class MorseTree<T> {

    private final Node<T> root;

    public MorseTree(T value) {
        this.root = new Node<>(value);
    }

    public Node<T> getRoot() {
        return root;
    }


    public void put(String code, T value) {
        recursivePut(this.root, code, 0, value);
    }

    private void recursivePut(Node<T> node, String code, int index, T value) {

        if (index == code.length()) {
            node.value = value;
            return;
        }

        char token = code.charAt(index);

        if (token == '.') {
            if (node.left == null) {
                node.left = new Node<>(null);
            }
            recursivePut(node.left, code, index + 1, value);
        } else if (token == '-') {
            if (node.right == null) {
                node.right = new Node<>(null);
            }
            recursivePut(node.right, code, index + 1, value);
        } else {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
    }

    public String encode(String text, Runnable redrawCallback) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            encoded.append(recursiveEncode(root, String.valueOf(c)));
            encoded.append(" ");
            redrawCallback.run();
        }

        return encoded.toString().trim();
    }

    public String decode(String morseCode, Runnable redrawCallback) {
        String[] morseArray = morseCode.split(" ");
        StringBuilder decoded = new StringBuilder();

        for (String morse : morseArray) {
            String result = recursiveDecode(root, morse);
            if (result == null) return null;
            decoded.append(result);
            redrawCallback.run();
        }

        return decoded.toString();
    }

    private String recursiveEncode(Node<T> node, String value) {
        if (node == null) return null;

        if (node.value != null && node.value.equals(value)) {
            node.circle.setStroke(Color.GREEN);
            return "";
        }

        String left = recursiveEncode(node.left, value);
        if (left != null) {
            node.circle.setStroke(Color.GREEN);
            return "." + left;
        }

        String right = recursiveEncode(node.right, value);
        if (right != null) {
            node.circle.setStroke(Color.GREEN);
            return "-" + right;
        }

        return null;
    }

    private String recursiveDecode(Node<T> node, String morse) {
        if (node == null) return null;
        node.circle.setStroke(Color.GREEN);
        if (morse.isEmpty()) return (String) node.value;

        char token = morse.charAt(0);

        return switch (token) {
            case '.' -> recursiveDecode(node.left, morse.substring(1));
            case '-' -> recursiveDecode(node.right, morse.substring(1));
            default -> throw new IllegalArgumentException("Invalid token: " + token);
        };
    }
}