package Dijkstra;
import java.util.*;

public class DijkstraTestRunner {

    static class Edge {
        int target, weight;
        public Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int id, distance;
        public Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    // New class to hold both distances and the path history
    static class Result {
        int[] distances;
        int[] parents;

        public Result(int[] distances, int[] parents) {
            this.distances = distances;
            this.parents = parents;
        }
    }

    public static Result dijkstra(int nodes, List<List<Edge>> adj, int source) {
        int[] dist = new int[nodes];
        int[] parent = new int[nodes]; // Track where we came from
        
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1); // -1 means no parent (or source node)
        dist[source] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(source, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int id = current.id;
            int distance = current.distance;

            if (distance > dist[id]) continue;

            List<Edge> neighbors = adj.get(id);
            for (int i = 0; i < neighbors.size(); i++) {
                Edge edge = neighbors.get(i);
                int node = edge.target;
                int weight = edge.weight;

                // Relaxation step
                if (dist[id] + weight < dist[node]) {
                    dist[node] = dist[id] + weight;
                    parent[node] = id; // Record the route!
                    queue.add(new Node(node, dist[node]));
                }
            }
        }
        return new Result(dist, parent);
    }

    public static void main(String[] args) {
        System.out.println("=== TEST CASE 1: 10 Vertices, Multiple Hops, Non-Obvious Route ===");
        runLargeNonObviousTest();

        System.out.println("\n=== TEST CASE 2: Unreachable Vertices in Disconnected Components ===");
        runUnreachableVerticesTest();

        System.out.println("\n=== TEST CASE 3: Multi-Hop Tradeoffs (Many Candidate Routes) ===");
        runMultiHopTradeoffTest();
    }

    // --------------------------------------------------------
    // Test Case Runners
    // --------------------------------------------------------

    private static void runLargeNonObviousTest() {
        int V = 10;
        List<List<Edge>> adj = initializeGraph(V);

        // Intentionally includes tempting direct edges that are not optimal.
        addEdge(adj, 0, 1, 10);
        addEdge(adj, 0, 2, 1);
        addEdge(adj, 2, 3, 2);
        addEdge(adj, 3, 1, 1);
        addEdge(adj, 3, 4, 5);
        addEdge(adj, 1, 4, 2);
        addEdge(adj, 4, 5, 1);
        addEdge(adj, 5, 6, 2);
        addEdge(adj, 6, 7, 1);
        addEdge(adj, 7, 8, 3);
        addEdge(adj, 8, 9, 1);
        addEdge(adj, 2, 7, 15);
        addEdge(adj, 0, 9, 30);
        addEdge(adj, 4, 9, 9);

        int source = 0;
        Result res = dijkstra(V, adj, source);

        printGraph(adj);
        printAllDistances(source, res.distances);
        System.out.println("\nShortest Paths to Target Vertices:");
        printPath(res, 1);
        printPath(res, 7);
        printPath(res, 9);

        printManualCheck(
            "Manual verification by inspection:",
            "To 1: 0 -> 2 -> 3 -> 1 = 1 + 2 + 1 = 4, better than direct 0 -> 1 = 10.",
            "To 7: 0 -> 2 -> 3 -> 1 -> 4 -> 5 -> 6 -> 7 = 10, better than 0 -> 2 -> 7 = 16.",
            "To 9: 0 -> 2 -> 3 -> 1 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 = 14, better than 0 -> 9 = 30."
        );
    }

    private static void runUnreachableVerticesTest() {
        int V = 9;
        List<List<Edge>> adj = initializeGraph(V);

        // Component A (reachable from source 0)
        addEdge(adj, 0, 1, 5);
        addEdge(adj, 1, 2, 3);
        addEdge(adj, 2, 3, 1);

        // Component B (disconnected from source 0)
        addEdge(adj, 4, 5, 2);
        addEdge(adj, 5, 6, 2);

        // Component C (isolated pair)
        addEdge(adj, 7, 8, 4);

        addEdge(adj, 3, 4, 8);

        int source = 0;
        Result res = dijkstra(V, adj, source);

        printGraph(adj);
        printAllDistances(source, res.distances);

        System.out.println("\nShortest Paths to Target Vertices:");
        printPath(res, 2);
        printPath(res, 4);
        printPath(res, 8);

        printManualCheck(
            "Manual verification by inspection:",
            "To 2: 0 -> 1 -> 2 = 8.",
            "To 4: 0 -> 1 -> 2 -> 3 -> 4 = 17.",
            "To 8: unreachable because there is no directed path from component A to component C."
        );
    }

    private static void runMultiHopTradeoffTest() {
        int V = 8;
        List<List<Edge>> adj = initializeGraph(V);

        addEdge(adj, 0, 1, 2);
        addEdge(adj, 0, 2, 6);
        addEdge(adj, 1, 2, 2);
        addEdge(adj, 1, 3, 5);
        addEdge(adj, 2, 3, 1);
        addEdge(adj, 2, 4, 4);
        addEdge(adj, 3, 5, 2);
        addEdge(adj, 4, 5, 1);
        addEdge(adj, 5, 6, 3);
        addEdge(adj, 1, 6, 20);
        addEdge(adj, 6, 7, 1);
        addEdge(adj, 3, 7, 10);

        int source = 0;
        Result res = dijkstra(V, adj, source);

        printGraph(adj);
        printAllDistances(source, res.distances);

        System.out.println("\nShortest Paths to Target Vertices:");
        printPath(res, 3);
        printPath(res, 6);
        printPath(res, 7);

        printManualCheck(
            "Manual verification by inspection:",
            "To 3: 0 -> 1 -> 2 -> 3 = 5, better than 0 -> 1 -> 3 = 7 and 0 -> 2 -> 3 = 7.",
            "To 6: 0 -> 1 -> 2 -> 3 -> 5 -> 6 = 10, much better than 0 -> 1 -> 6 = 22.",
            "To 7: 0 -> 1 -> 2 -> 3 -> 5 -> 6 -> 7 = 11, better than using edge 3 -> 7 (cost 10 from node 3 alone)."
        );
    }

    // --------------------------------------------------------
    // Helpers for clean code
    // --------------------------------------------------------

    private static List<List<Edge>> initializeGraph(int vertices) {
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
        return adj;
    }

    private static void addEdge(List<List<Edge>> adj, int source, int target, int weight) {
        adj.get(source).add(new Edge(target, weight));
    }

    private static void printGraph(List<List<Edge>> adj) {
        System.out.println("Graph (directed edges with weights):");
        for (int source = 0; source < adj.size(); source++) {
            List<Edge> edges = adj.get(source);
            if (edges.isEmpty()) {
                System.out.println("Node " + source + " -> (no outgoing edges)");
                continue;
            }

            StringBuilder row = new StringBuilder();
            row.append("Node ").append(source).append(" -> ");
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                row.append(edge.target).append("(").append(edge.weight).append(")");
                if (i < edges.size() - 1) {
                    row.append(", ");
                }
            }
            System.out.println(row);
        }
    }

    private static void printAllDistances(int source, int[] distances) {
        System.out.println("Distances from Node " + source + ":");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("Node " + i + " -> Unreachable");
            } else {
                System.out.println("Node " + i + " -> " + distances[i]);
            }
        }
    }

    /**
     * Traces the parent array backwards from the target to the source.
     */
    private static void printPath(Result res, int target) {
        if (res.distances[target] == Integer.MAX_VALUE) {
            System.out.println("Path to Node " + target + ": No valid path.");
            return;
        }

        List<Integer> path = new ArrayList<>();
        // Step backwards using the parent array
        for (int at = target; at != -1; at = res.parents[at]) {
            path.add(at);
        }
        
        // Reverse it so it reads from Source to Target
        Collections.reverse(path);
        
        System.out.println("Path to Node " + target + " (Cost: " + res.distances[target] + ") | Route: " + 
                           path.toString().replace("[", "").replace("]", "").replace(", ", " -> "));
    }

    private static void printManualCheck(String title, String check1, String check2, String check3) {
        System.out.println("\n" + title);
        System.out.println("- " + check1);
        System.out.println("- " + check2);
        System.out.println("- " + check3);
    }
}
