package org.example.algorithms;

import org.example.metrics.PerformanceTracker;

import java.util.List;

/**
 * Implementation of a generic Max-Heap data structure.
 * Provides insert, extractMax, increaseKey, and utility operations.
 * @param <T> type parameter extending Comparable
 */public class MaxHeap<T extends Comparable<T>> implements IMaxHeap<T> {

    /** Internal array to store heap elements */
    private T[] heap;

    /** Current number of elements in the heap */
    private int size;

    /** Tracker for performance metrics (comparisons, swaps, array accesses) */
    private PerformanceTracker tracker;

    /**
     * Constructs a MaxHeap with given initial capacity and performance tracker.     *
     * @param capacity initial size of the heap array
     * @param tracker performance tracker instance
     */
    public MaxHeap(int capacity, PerformanceTracker tracker) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
        this.tracker = tracker;
    }

    /**
     * Inserts a new value into the heap.
     * Automatically expands capacity if needed.
     * Time complexity: O(log n)
     * * @param value value to insert
     * @throws IllegalArgumentException if value is null
     */
    @Override
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null");
        }

        // Resize array if full
        if (size == heap.length) {
            encureCapacity();
        }

        heap[size] = value;
        tracker.incArrayAccesses(1);

        // Maintain heap property by moving element upward
        bubbleUp(size);
        size++;
    }

    /**
     * Swaps two elements in the heap.     *
     * @param i first index
     * @param j second index
     */
    public void swap(int i, int j) {
        tracker.incSwap();
        tracker.incArrayAccesses(4);

        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Moves an element up until heap property is restored.
     * Optimization: Instead of swapping elements multiple times (which would increment swap count),
     * * we store the element in a local variable and shift parent elements downward.
     * * Only one assignment is made at the final position, reducing array accesses and swaps.
     * * Time complexity: O(log n)
     * * @param index index of element to bubble up
     */
    public void bubbleUp(int index) {
        T value = heap[index];
        tracker.incArrayAccesses(1);

        while (index > 0) {
            int p = parent(index);
            tracker.incComparisons();
            if (value.compareTo(heap[p]) <= 0) break;

            heap[index] = heap[p];
            tracker.incArrayAccesses(1);
            index = p;
        }
        heap[index] = value;
        tracker.incArrayAccesses(1);
    }

    /**
     * Doubles heap capacity when full.
     * Preserves all current elements.
     * Time complexity: O(n)
     */
    public void encureCapacity() {
        int newCap = heap.length * 2 + 1;
        T[] newHeap = (T[]) new Comparable[newCap];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        tracker.incArrayAccesses(heap.length); // сохраняем трекинг
        heap = newHeap;
    }

    /**
     * Inserts multiple elements into the heap sequentially.
     * @param values list of elements
     */
    public void batchInsert(List<T> values) {
        for (T val : values) insert(val);
    }

    /**
     * Returns the index of the parent node.
     * @param index current node index
     * @return parent index
     */
    public int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Removes and returns the maximum (root) element from the heap.
     * Time complexity: O(log n)
     * @return maximum element
     * @throws IllegalStateException if heap is empty
     */
    @Override
    public T extractMax() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        T max = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) bubbleDown(0);
        return max;
    }

    /**
     * Moves an element down until heap property is restored.
     * Time complexity: O(log n)
     * @param index index of element to bubble down
     */
    public void bubbleDown(int index) {
        T value = heap[index];
        tracker.incArrayAccesses(1);

        int half = size / 2;
        while (index < half) {
            int left = index * 2 + 1;
            int right = left + 1;
            int largest = left;

            tracker.incComparisons();
            if (right < size && heap[right].compareTo(heap[left]) > 0) largest = right;

            tracker.incComparisons();
            if (value.compareTo(heap[largest]) >= 0) break;

            heap[index] = heap[largest];
            tracker.incArrayAccesses(1);
            index = largest;
        }

        heap[index] = value;
        tracker.incArrayAccesses(1);
    }

    /**
     * Returns (but does not remove) the maximum element.
     * @return top element
     * @throws IllegalStateException if heap is empty
     */
    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        tracker.incArrayAccesses(1);
        return heap[0];
    }

    /**
     * Increases the key at given index to a new, higher value.
     * * Maintains heap property by bubbling up.
     * @param index index of element to increase
     * @param newValue new higher value
     * @throws IllegalArgumentException if new value <= old value
     * @throws IndexOutOfBoundsException if index invalid
     */
    @Override
    public void increaseKey(int index, T newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        tracker.incComparisons();
        if (heap[index].compareTo(newValue) >= 0) {
            throw new IllegalArgumentException("New value must be greater than current");
        }

        heap[index] = newValue;
        tracker.incArrayAccesses(1);

        // Rebalance upward
        bubbleUp(index);
    }

    /**
     * Returns the maximum element without removal.
     * @return max element
     */
    public T getMax() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        tracker.incArrayAccesses(1);
        return heap[0];
    }

    /**
     * @return current heap size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true if heap has no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}