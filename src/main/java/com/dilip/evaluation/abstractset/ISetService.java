package com.dilip.evaluation.abstractset;

public interface ISetService<T extends Comparable<T>> {
    boolean AddItem(T t);

    boolean RemoveItem(T t);

    boolean HasItem(T t);
}
