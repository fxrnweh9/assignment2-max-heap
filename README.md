# Max-Heap Implementation (Assignment 2: Max-Heap Data Structures)

## Project Overview

This repository contains the individual Java implementation of the **Max-Heap** (Maximum Heap) data structure, developed as part of the Algorithmic Analysis course assignment.

The implementation focuses on efficient priority management with key operations:

  * `insert(T value)`
  * `extractMax()`
  * `increaseKey(int index, T newValue)`

In line with the assignment requirements, the code includes a **PerformanceTracker** to collect metrics (comparisons, swaps, array accesses) and a **CLI interface** for empirical performance testing.

| **Algorithm** | **Student** | **Partner (Analysis)** |
| :------------ | :---------- | :--------------------- |
| Max-Heap | Student B | Student A (Min-Heap) |

-----

## Algorithm Implementation: Max-Heap

### Key Structures

1.  **`MaxHeap<T extends Comparable<T>>`**: The core class implementing a binary heap where every parent node is greater than or equal to its children. Elements are stored in an array.
2.  **`PerformanceTracker`**: A class dedicated to tracking key operations:
      * `comparisons` (key comparisons)
      * `swaps` (element swaps)
      * `arrayAccesses` (array read/write operations)

### Optimization Techniques

To enhance the efficiency of `insert` (via `bubbleUp`) and `extractMax` (via `bubbleDown`), specific optimizations were applied:

  * **`bubbleUp` Optimization**: Instead of multiple `swap` operations (which cost 3 array accesses and 1 swap per level), the element is temporarily stored in a local variable. Parent elements are then shifted down until the correct insertion point is found. This reduces the number of full swaps. The operation effectively replaces $O(\log n)$ swaps with **1 final assignment** (swap count reduction) and $O(\log n)$ assignments (array accesses).
  * **Dynamic Capacity**: The `encureCapacity` method is called when the array is full, doubling the array size to maintain an $O(1)$ amortized cost for insertion.

### Asymptotic Complexity of Operations

| Operation | Best Case $(\Omega)$ | Worst Case $(O)$ | Average Case $(\Theta)$ | Auxiliary Space (Space) |
| :-------- | :------------------- | :----------------- | :---------------------- | :---------------------- |
| `insert` | $\Omega(1)$ (no bubbling) | $O(\log n)$ | $\Theta(\log n)$ | $O(1)$ |
| `extractMax` | $\Omega(\log n)$ | $O(\log n)$ | $\Theta(\log n)$ | $O(1)$ |
| `increaseKey` | $\Omega(1)$ (no bubbling) | $O(\log n)$ | $\Theta(\log n)$ | $O(1)$ |
| `peek` / `getMax` | $\Theta(1)$ | $\Theta(1)$ | $\Theta(1)$ | $O(1)$ |

**Analysis Justification:**

  * **Time Complexity**: The primary operations (`insert`, `extractMax`, `increaseKey`) involve traversing the **height** of the heap. Since the heap is a nearly complete binary tree, its height is $\lceil \log_2 (n+1) \rceil$, resulting in $\mathbf{O(\log n)}$ complexity in the worst and average cases.
  * **Space Complexity**: The implementation is **in-place** concerning the heap structure (stored in an array). $\mathbf{O(1)}$ auxiliary space is required for temporary variables during `bubbleUp` and `bubbleDown`. While `encureCapacity` temporarily uses $O(n)$ space for array copying, the amortized space cost remains low.

-----

## Build and Run Instructions

The project uses **Maven** for dependency management and building.

### Prerequisites

  * Java Development Kit (JDK) 8+
  * Apache Maven

### 1\. Build the Project

To compile the classes and create the executable JAR file, run:

```bash
mvn clean install
```

### 2\. Run Unit Tests

To verify the algorithm's correctness and coverage of edge cases, run:

```bash
mvn test
```

### 3\. Run Benchmark (CLI)

Use the `BenchmarkRunner` for empirical performance validation. The default input size is $N=10000$.

**Run with default size (N=10000):**

```bash
java -jar target/assignment2-[algorithm-name]-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**Run with a specified size (e.g., N=100000):**

```bash
java -jar target/assignment2-[algorithm-name]-1.0-SNAPSHOT-jar-with-dependencies.jar 100000
```

### Example CLI Output

```
Inserted 10000 elements.
Time elapsed: 4.567 ms
Comparisons: 110257
Swaps: 10000
Array accesses: 330771
```

-----

## Repository Structure

The repository structure complies with the assignment's requirements:

```
assignment2-maxheap/
├── src/main/java/
│   ├── algorithms/
│   │   └── MaxHeap.java           # Core Max-Heap implementation
│   ├── metrics/
│   │   └── PerformanceTracker.java  # Performance metrics collection
│   └── cli/
│       └── BenchmarkRunner.java   # Command-Line Interface for benchmarking
├── src/test/java/
│   └── algorithms/
│       └── MaxHeapTest.java       # Unit tests and correctness validation
├── docs/
│   ├── analysis-report.pdf      # Individual Analysis Report (PDF)
│   └── performance-plots/       # Performance plots
├── README.md                    # This file
└── pom.xml                      # Maven configuration
```

-----

## Git Workflow and Commit History

The commit history follows the required semantic style (Conventional Commits) and branching strategy.

**Example Commit Storyline:**

  * `init: maven project structure, junit5, ci setup`
  * `feat(metrics): performance counters and CSV export`
  * `feat(algorithm): baseline Max-Heap implementation with insert/extractMax`
  * `feat(algorithm): implement increaseKey operation`
  * `feat(optimization): optimize bubbleUp to reduce array swaps`
  * `test(algorithm): comprehensive test suite with edge cases and stress test`
  * `feat(cli): benchmark runner with configurable input sizes`
  * `fix(edge-cases): handle empty heap in peek/extractMax`
  * `docs(readme): initial readme and complexity analysis`
  * `perf(benchmark): run initial benchmarks and confirm O(log n) performance`
  * `release: v1.0 complete Max-Heap implementation`
