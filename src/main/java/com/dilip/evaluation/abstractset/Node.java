package com.dilip.evaluation.abstractset;

public class Node<K extends Comparable<K>, V> {
    K key;
    V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
