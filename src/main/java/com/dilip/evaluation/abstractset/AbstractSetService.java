package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractSetService<T extends Comparable<T>> {
    @Autowired
    AbstractSetImpl<T> set;

    public boolean HasItem(T t) {
        return set.HasItem(t);
    }

    public boolean AddItem (T t) {
        return set.AddItem(t);
    }

    public boolean RemoveItem(T t) {
        return set.RemoveItem(t);
    }
}
