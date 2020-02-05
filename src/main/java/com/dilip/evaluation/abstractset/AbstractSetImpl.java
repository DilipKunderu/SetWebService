package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class AbstractSetImpl<T extends Comparable<T>> extends AbstractSet<T> implements ISet<T> {
    private static final Object DUMMY = new Object();
    private int size;
    private List<List<Node<T, ?>>> buckets;

    private final int initialCapacity, threshold;
    private final float loadFactor;

    @Autowired
    public AbstractSetImpl (@Value("${spring.application.initialcapacity}") String size, @Value("${spring.application.loadfactor}") String loadFactor, @Value("${spring.application.threshold}") String threshold) {
        this.initialCapacity = Integer.parseInt(size);
        this.buckets = new ArrayList<>(this.initialCapacity);
        this.loadFactor = Float.parseFloat(loadFactor);
        this.threshold = Integer.parseInt(threshold);
        this.size = 0;
        initializeBuckets();
    }

    private void initializeBuckets() {
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets.add(new ArrayList<>(threshold));
        }
    }

    @Override
    public boolean AddItem(T t) {
        int bucketIdx = getBucket(t);
        int elemIdx = checkForExistence(t, bucketIdx);

        if (elemIdx == -1) {
            Node<T, Object> node = new Node<>(t, DUMMY);
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
//        Node<T, ?> node = checkForExistence(t, bucketIdx);
        int elemIdx = checkForExistence(t, bucketIdx);
        if (elemIdx != -1) {
            removeFromBucketList(bucketIdx, elemIdx);
            size--;
            return true;
        }
        return false;
    }

    private void removeFromBucketList(int bucketIdx, int elemIdx) {
        List<Node<T, ?>> l = this.buckets.get(bucketIdx);
        int lastIdx = l.size() - 1;
        Collections.swap(l, elemIdx, lastIdx); //need to check if this is constant time or more
        l.remove(lastIdx);
    }

    @Override
    public boolean HasItem(T t) {
        if (this.size == 0) {
            return false;
        }

        int bucketIdx = getBucket(t);
        return checkForExistence(t, bucketIdx) != -1;
    }

    private int checkForExistence(T t, int idx) {
        AtomicInteger pos = new AtomicInteger(-1);

        //might use object later
        T element = this.buckets.get(idx).stream()
                .map(x -> x.key)
                .peek(x -> pos.incrementAndGet())
                .filter(t::equals)
                .findFirst()
                .orElse(null);
//        Node<T, ?> node = this.buckets.get(idx)
//                .stream()
//                .filter(x->x.value.equals(t)).collect(onlyOneElement());
        return pos.get();
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

//    public static <T> Collector<T, ?, T> onlyOneElement () {
//        return Collectors.collectingAndThen(
//                Collectors.toList(), list -> {
//                    if(list.size() != 1) {
//                        try {
//                            throw new IllegalAccessException();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    return list.get(0);
//                }
//        );
//    }
}
