package com.dilip.evaluation.abstractset;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class ISetServiceTest {
    private ISetService<String> set;

    @BeforeEach
    void setUp() {
        int initialCapacity = 19;
        double loadFactor = 0.75;
        int numThreads = 2;
        set = new AbstractSetServiceImpl<>(initialCapacity, loadFactor, numThreads);
    }

    @Test
    void sizeReturnsInt() {
        assertThat(set.size(), CoreMatchers.instanceOf(Integer.class));
    }

    @Test
    void addItemReturnsTrueWhenNewArtifactIsAdded() {
        assertThat(set.size(), CoreMatchers.equalTo(0));
        String s = "Dilip";
        assertThat(set.AddItem(s), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.not(0));
        assertThat(set.size(), CoreMatchers.equalTo(1));
    }

    @Test
    void addItemReturnsFalseWhenSameArtifactAdded() {
        set.AddItem("Dilip");
        set.AddItem("Andrew");
        set.AddItem("Bob");
        assertThat(set.AddItem("Andrew"), CoreMatchers.is(false));
        assertThat(set.AddItem("Bob"), CoreMatchers.is(false));
        assertThat(set.AddItem("Dilip"), CoreMatchers.is(false));
    }

    @Test
    void addItemConsistentlyReturnsFalseWhenAddingSameArtifact() {
        set.AddItem("Dilip");
        assertThat(set.size(), CoreMatchers.equalTo(1));
        assertThat(set.AddItem("Dilip"), CoreMatchers.is(false));
    }

    @Test
    void removeItemReturnsTrueWhenExistingArtifactIsRemoved() {
        assertThat(set.size(), CoreMatchers.equalTo(0));
        set.AddItem("Dilip");
        assertThat(set.size(), CoreMatchers.equalTo(1));
        assertThat(set.RemoveItem("Dilip"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(0));
    }

    @Test
    void removeItemReturnsFalseWhenNonExistentObjectIsRemoved () {
        set.AddItem("Dilip");
        assertThat(set.size(), CoreMatchers.equalTo(1));
        assertThat(set.RemoveItem("Bob"), CoreMatchers.is(false));
        assertThat(set.size(), CoreMatchers.equalTo(1));
    }

    @Test
    void removeItemReturnsFalseConsistentlyIfNonExistentArtifact () {
        assertThat(set.size(), CoreMatchers.equalTo(0));
        set.AddItem("Dilip");
        assertThat(set.RemoveItem("Dilip"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(0));
        assertThat(set.RemoveItem("Dilip"), CoreMatchers.is(false));
        assertThat(set.RemoveItem("Dilip"), CoreMatchers.is(false));
    }

    @Test
    void hasItemReturnsTrueWhenArtifactExists() {
        set.AddItem("Dilip");
        assertThat(set.size(), CoreMatchers.equalTo(1));
        assertThat(set.HasItem("Dilip"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(1));
    }

    @Test
    void hasItemReturnsFalseWhenArtifactDoesNotExist () {
        set.AddItem("Dilip");
        set.AddItem("Andrew");
        assertThat(set.HasItem("Bob"), CoreMatchers.is(false));
    }

    @Test
    void hasItemConsistentlyResponds() {
        assertThat(set.size(), CoreMatchers.equalTo(0));
        set.AddItem("Dilip");
        assertThat(set.HasItem("Dilip"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(1));

        set.AddItem("Andrew");
        assertThat(set.HasItem("Dilip"), CoreMatchers.is(true));
        assertThat(set.HasItem("Andrew"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(2));

        set.RemoveItem("Dilip");
        assertThat(set.HasItem("Dilip"), CoreMatchers.is(false));
        assertThat(set.HasItem("Andrew"), CoreMatchers.is(true));
        assertThat(set.size(), CoreMatchers.equalTo(1));

        set.RemoveItem("Andrew");
        assertThat(set.HasItem("Andrew"), CoreMatchers.is(false));
        assertThat(set.size(), CoreMatchers.equalTo(0));
    }
}