package GraphRep;
import java.util.*;


public class AdjacencyListGraph implements Graph {
    private Map<Integer, List<Integer>> adjList;

    public AdjacencyListGraph() {
        this.adjList = new HashMap<>();
    }

@Override
public void addVertex(int vertex) {
    if (!adjList.containsKey(vertex)) {
        adjList.put(vertex, new ArrayList<>());
    }
}

@Override
public void addEdge(int v1, int v2, boolean directed) {
    addVertex(v1);
    addVertex(v2);
    adjList.get(v1).add(v2);
    if (!directed) {
        adjList.get(v2).add(v1);
    }
}

@Override
public void removeEdge(int v1, int v2) {
    if (adjList.containsKey(v1)) {
        adjList.get(v1).remove(Integer.valueOf(v2));
    }
    if (adjList.containsKey(v2)) {
        adjList.get(v2).remove(Integer.valueOf(v1));
    }
}

@Override
public boolean hasEdge(int v1, int v2) {
    return adjList.containsKey(v1) && adjList.get(v1).contains(v2);
}

@Override
public List<Integer> getNeighbors(int vertex) {
    return adjList.getOrDefault(vertex, new ArrayList<>());
} 

@Override
public void printGraph() {
    Object[] keys = adjList.keySet().toArray();
    for (int i = 0; i < keys.length; i++) {
        Integer vertex = (Integer) keys[i];
        List<Integer> neighbors = adjList.get(vertex);
        System.out.println(vertex + " -- " + neighbors);
    }
}
}