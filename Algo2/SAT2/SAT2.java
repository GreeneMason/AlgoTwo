package SAT2;
import java.util.*; 
public class SAT2 {
    private int n; // Nodes
    private List<List<Integer>> graph;
    private List<List<Integer>> reverseGraph;
    
    private int[] sccIndex; 
    private int sccCount;
    private boolean[] assignment;

    //initializing the graph and reverse graph
    public SAT2(int n) {
        this.n = n;
        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();
        int totalNodes = 2 * n + 1;
        for (int i = 0; i < totalNodes; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }
        sccIndex = new int[totalNodes];
        Arrays.fill(sccIndex, -1);
        assignment = new boolean[n+1];
    }



    //Sorting literals into the graph and reverse graph
    private int getIndex(int literal) {
        if (literal > 0) {
            return literal;
        }
        return Math.abs(literal) + n;
    }

    private int getNode(int index) {
    if (index <= n) {
        // If the index is in the lower half (indices 1 through n), it represents a positive variable
        return index + n; 
    } 
    else {
        // If the index is in the upper half (indices n+1 through 2n), it represents a negated variable
        return index - n; 
    }
}

    public void addClause(int u, int v) {
        int uId = getIndex(u);
        int vId = getIndex(v);

        //Clause (u OR v) means; NOT u -> v
        graph.get(getNode(uId)).add(vId);
        reverseGraph.get(vId).add(getNode(uId));

        //And; NOT v -> u
        graph.get(getNode(vId)).add(uId);
        reverseGraph.get(uId).add(getNode(vId));
    }

    private void findSCCs() {
        int totalNodes = 2 * n + 1;
        boolean[] visited = new boolean[totalNodes];
        Stack<Integer> stack = new Stack<>();

        // Pass 1: DFS on the original graph to fill the stack
        for (int i = 1; i < totalNodes; i++) {
            if (!visited[i]) {
                helper1(i, visited, stack);
            }
        }

        // Pass 2: DFS on the reversed graph
        sccCount = 0;
        while (!stack.isEmpty()) {

            int node = stack.pop();

            if (sccIndex[node] == -1) {
                sccCount++;
                helper2(node, sccCount);
            }
        }
    }


    private void helper1(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        List<Integer> neighbors = graph.get(node);
    for (int i = 0; i < neighbors.size(); i++) {
        int neighbor = neighbors.get(i);
    
        if (!visited[neighbor]) {
            helper1(neighbor, visited, stack);
        }
    }
    stack.push(node); // Push only after all neighbors are explored
}
    
    private void helper2(int node, int scc) {

        sccIndex[node] = scc;
        List<Integer> neighbors = reverseGraph.get(node);

        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            
            // Only visit if it hasn't been assigned an SCC yet
            if (sccIndex[neighbor] == -1) {
                helper2(neighbor, scc);
            }
        }
    }

    public boolean solve() {
        findSCCs();
        for (int i = 1; i <= n; i++) {
            if (sccIndex[i] == sccIndex[getNode(i)]) {
                //the variable and its negation are in the same SCC thus unsolvable
                return false; 
            }
        }
        for (int i = 1; i <= n; i++) {
            //true if the variable's SCC comes after its negation's SCC
            assignment[i] = sccIndex[i] > sccIndex[getNode(i)];
        }
        return true;
    }

    public boolean[] getAssignment() {
        return assignment;
    }
}
