package Algo2.MinSpanTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		System.out.println("=== Minimum Spanning Tree Tests ===");

		runTestCaseOne();
		runTestCaseTwo();
	}

	private static void runTestCaseOne() {
		int vertices = 9;
		List<List<prim.Edge>> graph = createGraph(vertices);

		// Classic weighted graph with 9 vertices.
		addUndirectedEdge(graph, 0, 1, 4);
		addUndirectedEdge(graph, 0, 7, 8);
		addUndirectedEdge(graph, 1, 2, 8);
		addUndirectedEdge(graph, 1, 7, 11);
		addUndirectedEdge(graph, 2, 3, 7);
		addUndirectedEdge(graph, 2, 8, 2);
		addUndirectedEdge(graph, 2, 5, 4);
		addUndirectedEdge(graph, 3, 4, 9);
		addUndirectedEdge(graph, 3, 5, 14);
		addUndirectedEdge(graph, 4, 5, 10);
		addUndirectedEdge(graph, 5, 6, 2);
		addUndirectedEdge(graph, 6, 7, 1);
		addUndirectedEdge(graph, 6, 8, 6);
		addUndirectedEdge(graph, 7, 8, 7);

		prim.result mst = prim.treeGraph(vertices, graph);
		printValidation("Test Case 1 (9 vertices)", vertices, mst, 37);
	}

	private static void runTestCaseTwo() {
		int vertices = 10;
		List<List<prim.Edge>> graph = createGraph(vertices);

		// A second weighted graph with 10 vertices.
		addUndirectedEdge(graph, 0, 1, 5);
		addUndirectedEdge(graph, 0, 2, 3);
		addUndirectedEdge(graph, 1, 3, 6);
		addUndirectedEdge(graph, 2, 3, 4);
		addUndirectedEdge(graph, 2, 4, 2);
		addUndirectedEdge(graph, 3, 5, 7);
		addUndirectedEdge(graph, 4, 5, 1);
		addUndirectedEdge(graph, 4, 6, 8);
		addUndirectedEdge(graph, 5, 7, 3);
		addUndirectedEdge(graph, 6, 7, 4);
		addUndirectedEdge(graph, 7, 8, 2);
		addUndirectedEdge(graph, 8, 9, 6);
		addUndirectedEdge(graph, 6, 9, 5);
		addUndirectedEdge(graph, 1, 9, 9);
		addUndirectedEdge(graph, 0, 9, 10);

		prim.result mst = prim.treeGraph(vertices, graph);
		printValidation("Test Case 2 (10 vertices)", vertices, mst, null);
	}

	private static List<List<prim.Edge>> createGraph(int vertices) {
		List<List<prim.Edge>> graph = new ArrayList<>();
		for (int i = 0; i < vertices; i++) {
			graph.add(new ArrayList<>());
		}
		return graph;
	}

	private static void addUndirectedEdge(List<List<prim.Edge>> graph, int u, int v, int w) {
		graph.get(u).add(new prim.Edge(u, v, w));
		graph.get(v).add(new prim.Edge(v, u, w));
	}

	private static boolean isConnectedMst(int vertices, List<prim.Edge> mstEdges) {
		if (vertices == 0) {
			return true;
		}

		List<List<Integer>> mstAdj = new ArrayList<>();
		for (int i = 0; i < vertices; i++) {
			mstAdj.add(new ArrayList<>());
		}

		for (prim.Edge e : mstEdges) {
			mstAdj.get(e.start()).add(e.end());
			mstAdj.get(e.end()).add(e.start());
		}

		boolean[] seen = new boolean[vertices];
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		seen[0] = true;
		queue.add(0);

		while (!queue.isEmpty()) {
			int node = queue.poll();
			for (int neighbor : mstAdj.get(node)) {
				if (!seen[neighbor]) {
					seen[neighbor] = true;
					queue.add(neighbor);
				}
			}
		}

		for (boolean v : seen) {
			if (!v) {
				return false;
			}
		}
		return true;
	}

	private static void printValidation(String name, int vertices, prim.result mst, Integer expectedWeight) {
		int edgeCount = mst.edges().size();
		boolean correctEdgeCount = edgeCount == vertices - 1;
		boolean connected = isConnectedMst(vertices, mst.edges());

		System.out.println("\n" + name);
		System.out.println("Vertices: " + vertices);
		System.out.println("MST edge count: " + edgeCount + " (expected " + (vertices - 1) + ")");
		System.out.println("Edge count check (n-1): " + correctEdgeCount);
		System.out.println("Connectivity check: " + connected);
		System.out.println("Total MST weight: " + mst.treeWeight());

		if (expectedWeight != null) {
			System.out.println("Expected MST weight: " + expectedWeight + " | Match: " + (expectedWeight == mst.treeWeight()));
		}

		boolean pass = correctEdgeCount && connected;
		if (expectedWeight != null) {
			pass = pass && expectedWeight == mst.treeWeight();
		}
		System.out.println("Result: " + (pass ? "PASS" : "FAIL"));
	}
}
