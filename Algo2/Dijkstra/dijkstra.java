package Dijkstra;
import java.util.*;
public class dijkstra {
    

    static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }

    }

    static class Node implements Comparable<Node> {
        int id;
        int distance;
        
        Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
    }

    //Dijkstras algorithm calculates the shortest path from a source node to all other nodes.
    public static int[] dijkstra(int nodes, List<List<Edge>> adj, int source) {
        int[] dist = new int[nodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(source, 0));
        
        // Process nodes in the priority queue until it is empty
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int id = current.id;
            int distance = current.distance;
            
            if (distance > dist[id]) continue;
            //A list of adjacent edges for the current node
            List<Edge> neighbors = adj.get(id);
            for (int i = 0; i < neighbors.size(); i++) {
                Edge edge = neighbors.get(i); // Get the edge at the current index
    
                int node = edge.target;
                int weight = edge.weight;

                // Relaxation step
                if (dist[id] + weight < dist[node]) {
                    dist[node] = dist[id] + weight;
                    queue.add(new Node(node, dist[node]));
                }
            }
        }
        
        return dist;
    }
}
