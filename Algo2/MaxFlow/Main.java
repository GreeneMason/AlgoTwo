package MaxFlow;

public class Main {
    public static void main(String[] args) { 

    // ==========================================
    // Test Case 1: Complex 6-Node Network
    // ==========================================
    FordFulk test1 = new FordFulk(6);
    int source1 = 0;
    int sink1 = 5;

    test1.addEdge(0, 1, 16); 
    test1.addEdge(0, 2, 13); 
    test1.addEdge(1, 2, 10);
    test1.addEdge(1, 3, 12); 
    test1.addEdge(2, 1, 4); 
    test1.addEdge(2, 4, 14); 
    test1.addEdge(3, 2, 9);  
    test1.addEdge(3, 5, 20); 
    test1.addEdge(4, 3, 7);  
    test1.addEdge(4, 5, 4);  

    System.out.println("Test Case 1 Expected: 23 | Actual: " + test1.fordFulkerson(source1, sink1)); 

    // ==========================================
    // Test Case 2: Multiple Paths
    // ==========================================
    FordFulk test2 = new FordFulk(5);
    int source2 = 0;
    int sink2 = 4;

    test2.addEdge(0, 1, 20);
    test2.addEdge(0, 2, 20);
    test2.addEdge(1, 3, 10);
    test2.addEdge(1, 4, 10);
    test2.addEdge(2, 3, 10);
    test2.addEdge(2, 4, 10);
    test2.addEdge(3, 4, 20);

    System.out.println("Test Case 2 Expected: 40 | Actual: " + test2.fordFulkerson(source2, sink2));

    // ==========================================
    // Test Case 3: The Backward Edge Trap
    // ==========================================
    FordFulk test3 = new FordFulk(4);
    int source3 = 0;
    int sink3 = 3;

    test3.addEdge(0, 1, 10);
    test3.addEdge(0, 2, 10);
    test3.addEdge(1, 2, 10); // The trap edge
    test3.addEdge(1, 3, 10);
    test3.addEdge(2, 3, 10);

    System.out.println("Test Case 3 Expected: 20 | Actual: " + test3.fordFulkerson(source3, sink3));
    }
}



