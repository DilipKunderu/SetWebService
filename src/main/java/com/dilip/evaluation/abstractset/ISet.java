package com.dilip.evaluation.abstractset;

public interface ISet<Member> {
    boolean AddItem(Member member);

    boolean RemoveItem(Member member);

    boolean HasItem(Member member);
}
