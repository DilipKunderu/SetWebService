package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
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
        return false;
    }

    @Override
    public boolean RemoveItem(T t) {
        return false;
    }

    @Override
    public boolean HasItem(T t) {
        int idx = getBucket(t);
        return checkForExistence(t) != null;
    }

    private List<Node<?, T>> checkForExistence(T t) {
        List<Node<?, T>> l = this.buckets
                .stream()
                .flatMap(x->x.stream())
                .filter(x->x.value.equals(t)).collect(Collectors.toList());
        return l;
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
}
