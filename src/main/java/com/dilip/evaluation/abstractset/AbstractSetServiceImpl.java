package com.dilip.evaluation.abstractset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AbstractSetServiceImpl<T extends Comparable<T>> extends AbstractSet<T> implements ISetService<T> {
    Logger logger = LoggerFactory.getLogger(AbstractSetServiceImpl.class);
    private static final Object DUMMY = new Object();
    private ExecutorService executorService;

    private final int initialCapacity;
    private final double loadFactor;
    private double currentLoadFactor;

    private final AtomicInteger size;

    //Need to distribute this
    private List<List<Node<T, ?>>> buckets;

    @Autowired
    AbstractSetServiceImpl(int initialCapacity, double loadFactor, int numThreads) {
        this.size = new AtomicInteger(0);
        this.initialCapacity = initialCapacity;
        this.buckets = Collections.synchronizedList(new ArrayList<>(this.initialCapacity));
        initializeBuckets();
        this.loadFactor = loadFactor;
        this.currentLoadFactor = 0.0;
        this.executorService = Executors.newFixedThreadPool(numThreads);
        logger.info("Set initialized");
    }

    private void computeCurrentLoadFactor() {
        this.currentLoadFactor = (this.size.get() * 1.0)/this.buckets.size();
    }

    private void initializeBuckets() {
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets.add(new LinkedList<>());
        }
        logger.info("buckets created");
    }

    @Override
    public boolean AddItem(T t) {

        Callable<Boolean> task = () -> {
            int bucketIdx = getBucket(t);
            Node<T, ?> node = checkForExistence(t, bucketIdx);

            if (node == null) {
                node = new Node<>(t, DUMMY);
                List<Node<T, ?>> l = this.buckets.get(bucketIdx);
                l.add(node);
                this.size.incrementAndGet();
                computeCurrentLoadFactor();

                if (currentLoadFactor >= loadFactor) {
                    createMoreBuckets();
                }
                return true;
            }
            return false;
        };
        return executeCall(task);
    }

    protected void createMoreBuckets() {
        logger.info("Expansion Started");
        // Can actually use nearest prime to 2 * prevSize for better prevention of sparse population of the buckets
        List<List<Node<T, ?>>> temp = this.buckets;

        this.buckets = Collections.synchronizedList(new ArrayList<>(2 * this.size.get()));
        initializeBuckets();

        temp.stream()
                .flatMap(Collection::stream)
                .map(y->y.key)
        .forEach(this::AddItem);
        logger.info("Expansion completed");
    }

    @Override
    public boolean RemoveItem(T t) {
        Callable<Boolean> task = () -> {
            int bucketIdx = getBucket(t);
            Node<T, ?> node = checkForExistence(t, bucketIdx);
            if (node != null) {
                removeFromBucketList(bucketIdx, node);
                this.size.decrementAndGet();
                return true;
            }
            return false;
        };

        return executeCall(task);
    }

    private void removeFromBucketList(int bucketIdx,Node<T, ?> node) {
        LinkedList<Node<T, ?>> l = (LinkedList<Node<T, ?>>) this.buckets.get(bucketIdx);
        l.remove(node);
    }

    @Override
    public boolean HasItem(T t) {
        Callable<Boolean> task = () -> {
            if (this.size.get() == 0) {
                return false;
            }

            int bucketIdx = getBucket(t);
            return checkForExistence(t, bucketIdx) != null;
        };

        return executeCall(task);
    }

    private boolean executeCall (Callable<Boolean> task) {
        Future<Boolean> future = this.executorService.submit(task);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn(e.getMessage().concat(" in executeCall method"));
            return false;
        }
    }

    protected Node<T, ?> checkForExistence(T t, int idx) {
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
        return this.size.get();
    }

    private int getBucket(T t) {
        int initialHash = Math.abs(t.hashCode());
        int offset = this.buckets.size();
        return initialHash % offset;
    }
}
