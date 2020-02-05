package com.dilip.evaluation.abstractset;

public interface ISet<String> {
    boolean AddItem(String member);

    boolean RemoveItem(String member);

    boolean HasItem(String member);
}
