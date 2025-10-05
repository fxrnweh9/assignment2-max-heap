package org.example.cli;

import org.example.algorithms.MaxHeap;
import org.example.metrics.PerformanceTracker;

import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int n = 10000;
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input size, using default 10000");
            }
        }

        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap<Integer> heap = new MaxHeap<>(n, tracker);
        Random rand = new Random();

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            heap.insert(rand.nextInt(n * 10));
        }
        long end = System.nanoTime();

        System.out.println("Inserted " + n + " elements.");
        System.out.println("Time elapsed: " + (end - start) / 1e6 + " ms");
        System.out.println("Comparisons: " + tracker.getComparisons());
        System.out.println("Swaps: " + tracker.getSwaps());
        System.out.println("Array accesses: " + tracker.getArrayAccesses());
    }
}
