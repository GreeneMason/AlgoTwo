package GraphRep;
import java.util.*;

public interface Graph {
    void addVertex(int vertex);
    void addEdge(int vertex1, int vertex2, boolean directed);
    void removeEdge(int vertex1, int vertex2);
    boolean hasEdge(int vertex1, int vertex2);
    List<Integer> getNeighbors(int vertex);
    void printGraph();
}

