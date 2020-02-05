package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AbstractSetImpl<T extends Comparable<T>> extends AbstractSet<T> implements ISet<T> {
    private List<List<Node<?, T>>> buckets;

    private final int initialCapacity;
    private final float loadFactor;

    @Autowired
    public AbstractSetImpl (@Value("${spring.application.initialcapacity}") String size, @Value("${spring.application.loadfactor}") String loadFactor) {
        this.initialCapacity = Integer.parseInt(size);
        this.buckets = new ArrayList<>(this.initialCapacity);
        this.loadFactor = Float.parseFloat(loadFactor);
    }

    @Override
    public boolean AddItem(T t) {
        Node<?, T> node = checkForExistence(t, getBucket(t));
        if (node == null) {

        }
        return false;
    }

    @Override
    public boolean RemoveItem(T t) {
        return false;
    }

    @Override
    public boolean HasItem(T t) {
        int idx = getBucket(t);
        return checkForExistence(t, idx) != null;
    }

    private Node<?, T> checkForExistence(T t, int idx) {

        Node<?, T> node = this.buckets.get(idx)
                .stream()
                .filter(x->x.value.equals(t)).collect(onlyOneElement());
        return node;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return this.buckets.size();
    }

    private int getBucket(T t) {
        return t.hashCode() % this.size();
    }

    public static <T> Collector<T, ?, T> onlyOneElement () {
        return Collectors.collectingAndThen(
                Collectors.toList(), list -> {
                    if(list.size() != 1) {
                        try {
                            throw new IllegalAccessException();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    return list.get(0);
                }
        );
    }
}
