package com.dilip.evaluation.abstractset;

public class Node<K, V extends Comparable<V>> {
    K key;
    V value;
    Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
