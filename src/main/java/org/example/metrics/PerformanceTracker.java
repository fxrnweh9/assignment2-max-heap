package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;

public class PerformanceTracker {

    private long swap;
    private long comparisons;
    private long arrayAccesses;

    public PerformanceTracker(){
        reset();
    }

    public void reset(){
        swap = 0;
        comparisons = 0;
        arrayAccesses = 0;
    }

    public void incComparisons(){
        comparisons++;
    }

    public void incSwap(){
        swap++;
    }

    public void incArrayAccesses(long count){
        arrayAccesses += count;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swap;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public void exportToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Comparisons,Swaps,ArrayAccesses\n");
            writer.append(comparisons + "," + swap + "," + arrayAccesses + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error while writing CSV: " + e.getMessage());
        }
    }
}
