package com.dilip.evaluation.abstractset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractSet;

class AbstractSetServiceTest {
    AbstractSet<String> abstractSet;
    private final String loadFactor = "0.75";
    private final String initialCapacity = "19";
    Node<String, Integer> obj1, obj2, obj3;

    @BeforeEach
    void setUp() {

        abstractSet = new AbstractSetServiceImpl<String>(initialCapacity, loadFactor);
        obj1 = new Node<>("Dilip", 1);
        obj2 = new Node<>("Hitesh", 2);
        obj3 = new Node<>("Andrew", 3);
    }

    @Test
    void hasItem() {

    }

    @Test
    void addItem() {
    }

    @Test
    void removeItem() {
    }
}