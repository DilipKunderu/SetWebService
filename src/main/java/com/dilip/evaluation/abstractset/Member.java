package com.dilip.evaluation.abstractset;

public class Member<T extends Comparable<T>> {
    private T t;

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }
}
