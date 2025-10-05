package org.example.algorithms;

public interface IMaxHeap <T extends Comparable <T>>{
    void insert(T value);

    T extractMax();

    void increaseKey(int index, T newValue);

    T getMax();

    int size();

    boolean isEmpty();

}
