package MaxFlow;
import java.util.*;
public class FordFulk {
    private int nodes;
    private int[][] flowMatrix;

    //Constructor method
    public FordFulk(int nodes) {
        this.nodes = nodes;
        this.flowMatrix = new int[nodes][nodes];
    }

    //A method to build the node networks
    public void addEdge(int x, int y, int capacity) {
        this.flowMatrix[x][y] = capacity;
    }
    //A depth-first search method to find augmenting paths
    private int dfs(int x, int end, boolean[] visited, int flowPath) {
        if (x == end) {
            return flowPath;
        }

        visited[x] = true;
        for (int i = 0; i < nodes; i++) {
            if (!visited[i] && flowMatrix[x][i] > 0) {
                int flow = dfs(i, end, visited, Math.min(flowPath, flowMatrix[x][i]));
                if (flow > 0) {
                    int fowardFlow = flowMatrix[x][i];
                    int undoFlow = flowMatrix[i][x];
                    flowMatrix[x][i] = fowardFlow - flow;
                    flowMatrix[i][x] = undoFlow + flow;

                    return flow;
                }
            }
        }
        return 0;
    }
    //The main method to calculate the maximum flow from start to end using the Ford-Fulkerson algorithm
    public int fordFulkerson(int start, int end) {
        int maxFlow = 0;
        boolean[] visited = new boolean[nodes];

        while (true) {
            Arrays.fill(visited, false); 
            int currentFlow = dfs(start, end, visited, 999999999);
            if (currentFlow == 0) { 
                break; 
            }

            maxFlow = currentFlow + maxFlow; 
        }
        return maxFlow;
    }
}
