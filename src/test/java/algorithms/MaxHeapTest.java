package algorithms;

import org.example.algorithms.MaxHeap;
import org.example.metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {

    private MaxHeap<Integer> heap;
    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new PerformanceTracker();
        heap = new MaxHeap<>(10, tracker);
    }

    @Test
    void testInsertAndPeek() {
        heap.insert(5);
        heap.insert(10);
        heap.insert(3);
        assertEquals(10, heap.peek());
    }

    @Test
    void testExtractMaxMaintainsHeap() {
        heap.insert(2);
        heap.insert(8);
        heap.insert(5);
        assertEquals(8, heap.extractMax());
        assertEquals(5, heap.peek());
        assertEquals(2, heap.size());
    }
    @Test
    void testMaxHeapStress() {
        int N = 1000;
        Random rand = new Random();

        for (int i = 0; i < N; i++) heap.insert(rand.nextInt(10000));

        int prev = heap.extractMax();
        while (!heap.isEmpty()) {
            int curr = heap.extractMax();
            assertTrue(prev >= curr, "Heap property violated");
            prev = curr;
        }
    }

    @Test
    void testIncreaseKey() {
        heap.insert(4);
        heap.insert(7);
        heap.insert(1);
        heap.increaseKey(2, 10);
        assertEquals(10, heap.peek());
    }

    @Test
    void testSizeAndIsEmpty() {
        assertTrue(heap.isEmpty());
        heap.insert(1);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
    }

    @Test
    void testExceptions() {
        assertThrows(IllegalStateException.class, heap::peek);
        assertThrows(IllegalStateException.class, heap::extractMax);

        heap.insert(3);
        assertThrows(IllegalArgumentException.class, () -> heap.increaseKey(0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(5, 10));
    }

    @Test
    void testHeapPropertyAfterMultipleOperations() {
        heap.insert(4);
        heap.insert(6);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);

        assertEquals(8, heap.extractMax());
        heap.increaseKey(2, 10);
        assertEquals(10, heap.peek());
    }

    @Test
    void testInsertNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> heap.insert(null));
    }

    @Test
    void testIncreaseKeyWithMaxValue() {
        heap.insert(10);
        heap.increaseKey(0, 20);
        assertEquals(20, heap.peek());
    }

    @Test
    void testExtractMaxUntilEmpty() {
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);

        assertEquals(3, heap.extractMax());
        assertEquals(2, heap.extractMax());
        assertEquals(1, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testIncreaseKeyInvalidIndex() {
        heap.insert(5);
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(5, 10));
    }
}
