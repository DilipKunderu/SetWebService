package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
public class AbstractSetImpl<T extends Comparable<T>> extends AbstractSet<T> implements ISet<T> {
    private static final Object DUMMY = new Object();
    private int size;
    private List<List<Node<T, ?>>> buckets;

    private final int initialCapacity;
    private final float loadFactor;

    @Autowired
    public AbstractSetImpl (@Value("${spring.application.initialcapacity}") String size, @Value("${spring.application.loadfactor}") String loadFactor) {
        this.initialCapacity = Integer.parseInt(size);
        this.buckets = new ArrayList<>(this.initialCapacity);
        this.loadFactor = Float.parseFloat(loadFactor);
        this.size = 0;
        initializeBuckets();
    }

    private void initializeBuckets() {
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets.add(new LinkedList<>());
        }
    }

    @Override
    public boolean AddItem(T t) {
        int bucketIdx = getBucket(t);
        Node<T, ?> node = checkForExistence(t, bucketIdx);

        if (node == null) {
            node = new Node<>(t, DUMMY);
            List<Node<T, ?>> l = this.buckets.get(bucketIdx);
            l.add(node);
            size++;

            return true;
        }
        return false;
    }

    @Override
    public boolean RemoveItem(T t) {
        int bucketIdx = getBucket(t);
        Node<T, ?> node = checkForExistence(t, bucketIdx);
        if (node != null) {
            removeFromBucketList(bucketIdx, node);
            size--;
            return true;
        }
        return false;
    }

    private void removeFromBucketList(int bucketIdx,Node<T, ?> node) {
        LinkedList<Node<T, ?>> l = (LinkedList<Node<T, ?>>) this.buckets.get(bucketIdx);
        l.remove(node);

    }

    @Override
    public boolean HasItem(T t) {
        if (this.size == 0) {
            return false;
        }

        int bucketIdx = getBucket(t);
        return checkForExistence(t, bucketIdx) != null;
    }

    private Node<T, ?> checkForExistence(T t, int idx) {
       Node<T, ?> node = this.buckets.get(idx).stream()
                .filter(x->x.key.equals(t))
                .findFirst()
                .orElse(null);

        return node;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    private int getBucket(T t) {
        int initialHash = Math.abs(t.hashCode());
        int offset = this.buckets.size();
        return initialHash % offset;
    }
}
