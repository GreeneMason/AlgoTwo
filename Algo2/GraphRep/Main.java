package GraphRep;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    System.out.println("AdjList");
    runSimpleTest(new AdjacencyListGraph(), false);
    runSimpleTest(new AdjacencyListGraph(), true);

    System.out.println("AdjMatrix");
    runSimpleTest(new AdjacencyMatrixGraph(50), false);
    runSimpleTest(new AdjacencyMatrixGraph(50), true);
}

public static void runSimpleTest(Graph g, boolean isDense) {
    //Build
    long start = System.nanoTime();

    if (isDense) {
    buildDense(g);
    } else {
    buildSparse(g);
    }

    System.out.println("Build Time: " + (System.nanoTime() - start) + " ns");

    //Print
    g.printGraph();

    // Query
    start = System.nanoTime();
    g.hasEdge(0, 1);
    System.out.println("Query Time: " + (System.nanoTime() - start) + " ns");

    // Removal
    start = System.nanoTime();
    g.removeEdge(0, 1);
    System.out.println("Remove Time: " + (System.nanoTime() - start) + " ns\n");
}

private static void buildSparse(Graph g) {
    for (int i = 0; i < 50; i++) {
        g.addVertex(i);
        if (i > 0) g.addEdge(i - 1, i, false);
    }
}

private static void buildDense(Graph g) {
    for (int i = 0; i < 50; i++) g.addVertex(i);
    for (int i = 0; i < 50; i++) {
        for (int j = 0; j < 50; j++) {
            if (i != j) g.addEdge(i, j, true);
        }
    }
}

}