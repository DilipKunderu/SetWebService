package com.dilip.evaluation.abstractset;

import org.springframework.stereotype.Component;

import java.util.AbstractSet;
import java.util.Iterator;

@Component
public class AbstractSetImpl<Member> extends AbstractSet<Member> implements ISet<Member> {

    @Override
    public boolean AddItem(Member member) {
        return false;
    }

    @Override
    public boolean RemoveItem(Member member) {
        return false;
    }

    @Override
    public boolean HasItem(Member member) {
        return false;
    }

    @Override
    public Iterator<Member> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
