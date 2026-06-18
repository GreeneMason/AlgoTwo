package Algo2.MinSpanTree;
import java.util.*;

public class prim {
    public record Edge(int start, int end, int weight) {}

    public record result(List<Edge> edges, int treeWeight) {}
    // 

    public static result treeGraph(int vertCount, List<List<Edge>> graph) {
        boolean[] visited = new boolean[vertCount];

        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight()));
        int totalCost = 0;
        List<Edge> treeEdges = new ArrayList<>();
        visited[0] = true;
       
        //Build the queue
        for(int i = 0; i<graph.get(0).size(); i++){
            Edge edge = graph.get(0).get(i);
            queue.add(edge);
        }
        //Traverse till end is found
        while (!queue.isEmpty()) {
            Edge currentEdge = queue.poll();
            int nextVertex = currentEdge.end;
            if (visited[nextVertex]) {
                continue;
            }
            //Record
            visited[nextVertex] = true;
            totalCost += currentEdge.weight();
            treeEdges.add(currentEdge);

            // Add all edges to the queue
            for (int i = 0; i < graph.get(nextVertex).size(); i++) {
                Edge neighborEdge = graph.get(nextVertex).get(i);
                if (!visited[neighborEdge.end()]) {
                    queue.add(neighborEdge);
                }
            }
        }
    return new result(treeEdges, totalCost);
    }
}

