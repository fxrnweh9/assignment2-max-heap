package org.example.algorithms;

import org.example.metrics.PerformanceTracker;

/**
 * Implementation of a generic Max-Heap data structure.
 * Provides insert, extractMax, increaseKey, and utility operations.
 *
 * @param <T> type parameter extending Comparable
 */
public class MaxHeap<T extends Comparable<T>> implements IMaxHeap<T> {

    /** Internal array to store heap elements */
    private T[] heap;

    /** Current number of elements in the heap */
    private int size;

    /** Tracker for performance metrics (comparisons, swaps, array accesses) */
    private PerformanceTracker tracker;

    /**
     * Constructs a MaxHeap with given initial capacity and performance tracker.
     *
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
     *
     * Time complexity: O(log n)
     *
     * @param value value to insert
     * @throws IllegalArgumentException if value is null
     */
    @Override
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value = null");
        }

        // Resize array if full
        if (size == heap.length) {
            encureCapacity();
        }

        heap[size] = value;
        tracker.incArrayAccesses(1);

        // Maintain heap property by moving element upward
        bubleUp(size);
        size++;
    }

    /**
     * Swaps two elements in the heap.
     *
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
     *
     * Time complexity: O(log n)
     *
     * @param index index of element to bubble up
     */
    public void bubleUp(int index) {
        while (index > 0) {
            int p = parent(index);

            // Compare with parent and swap if greater
            if (heap[index].compareTo(heap[p]) > 0) {
                swap(index, p);
                index = p;
            } else {
                break;
            }
        }
    }

    /**
     * Doubles heap capacity when full.
     * Preserves all current elements.
     *
     * Time complexity: O(n)
     */
    public void encureCapacity() {
        int newCap = heap.length * 2 + 1;
        T[] newHeap = (T[]) new Comparable[newCap];

        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
            tracker.incArrayAccesses(1); // track data copy
        }
        heap = newHeap;
    }

    /**
     * Returns the index of the parent node.
     *
     * @param index current node index
     * @return parent index
     */
    public int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Removes and returns the maximum (root) element from the heap.
     *
     * Time complexity: O(log n)
     *
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

        tracker.incArrayAccesses(3);

        // Restore heap property from root downward
        bubleDown(0);
        return max;
    }

    /**
     * Moves an element down until heap property is restored.
     *
     * Time complexity: O(log n)
     *
     * @param index index of element to bubble down
     */
    public void bubleDown(int index) {
        while (index < size) {
            int left = (index * 2) + 1;   // left child
            int right = (index * 2) + 2;  // right child
            int largest = index;

            // Compare with left child
            if (left < size) {
                tracker.incComparisons();
                if (heap[left].compareTo(heap[largest]) > 0) {
                    largest = left;
                }
            }

            // Compare with right child
            if (right < size) {
                tracker.incComparisons();
                if (heap[right].compareTo(heap[largest]) > 0) {
                    largest = right;
                }
            }

            // Swap if needed
            if (largest != index) {
                swap(index, largest);
                tracker.incArrayAccesses(2);
                index = largest;
            } else {
                break;
            }
        }
    }

    /**
     * Returns (but does not remove) the maximum element.
     *
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
     * Maintains heap property by bubbling up.
     *
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
        bubleUp(index);
    }

    /**
     * Returns the maximum element without removal.
     *
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
