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

    public void put(Node<T> node, String code, T value) {
        Node<T> curr = node;
        for (char token : code.toCharArray()) {
            curr = switch (token) {
                case '.' -> {
                    if (curr.left == null) curr.left = new Node<>(null);
                    yield curr.left;
                }
                case '-' -> {
                    if (curr.right == null) curr.right = new Node<>(null);
                    yield curr.right;
                }
                default -> throw new IllegalArgumentException("Invalid token: " + token);
            };
        }
        curr.value = value;
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

        if (node.value.equals(value)) {
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
