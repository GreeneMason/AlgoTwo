package GraphRep;
import java.util.*;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] matrix;
    private int numVert;
    private int maxVert;
    
    public AdjacencyMatrixGraph(int maxVert) {
        this.maxVert = maxVert;
        this.matrix = new int[maxVert][maxVert];
        this.numVert = 0;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= maxVert) {
            throw new IllegalArgumentException("Not enough spcae");
        }
        numVert++;
    }

    @Override
    public void addEdge(int v1, int v2, boolean directed) {
        if (v1 >= numVert || v2 >= numVert) {
            throw new IllegalArgumentException("Vertex index exceeds limit");
        }
        matrix[v1][v2] = 1;
        if (!directed) {
            matrix[v2][v1] = 1;
        }
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (v1 >= numVert || v2 >= numVert) {
            throw new IllegalArgumentException("Vertex index is out of bounds");
        }
        matrix[v1][v2] = 0;
        matrix[v2][v1] = 0;
    }

    @Override
    public boolean hasEdge(int v1, int v2) {
        if (v1 >= numVert || v2 >= numVert) {
            throw new IllegalArgumentException("Illegal vertex index");
        }
        return matrix[v1][v2] == 1;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex >= numVert) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numVert; i++) {
            if (matrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void printGraph() {
        for (int i = 0; i < numVert; i++) {
            List<Integer> neighbors = getNeighbors(i);
            System.out.println(i + " -- " + neighbors);
        }
    }


}
