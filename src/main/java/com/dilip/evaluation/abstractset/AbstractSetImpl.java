package com.dilip.evaluation.abstractset;

import org.springframework.stereotype.Component;

import java.util.AbstractSet;
import java.util.Iterator;

@Component
public class AbstractSetImpl extends AbstractSet<String> implements ISet<String> {
    String root;

    public AbstractSetImpl() {
        this.root = null;
    }

    @Override
    public boolean AddItem(String member) {
        return false;
    }

    @Override
    public boolean RemoveItem(String member) {
        return false;
    }

    @Override
    public boolean HasItem(String member) {

        return false;
    }

    @Override
    public Iterator<String> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
