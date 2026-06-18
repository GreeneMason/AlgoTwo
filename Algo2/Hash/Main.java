package Algo2.Hash;

public class Main {
	public static void main(String[] args) {
		System.out.println("=== Hash Table Test Suite ===");

		testBasicHashOperations();
		testCollisionHandling();
		testPerfectHashStaticDataset();
		comparePerformance();
	}

	private static void testBasicHashOperations() {
		System.out.println("\n1) Basic hash table: 50+ insert/search/delete operations");

		BasicHash<Integer, String> hash = new BasicHash<>(101);
		int n = 60;
		int passed = 0;
		int total = n * 3;

		for (int i = 1; i <= n; i++) {
			hash.insert(i, "V" + i);
			passed++;
		}

		for (int i = 1; i <= n; i++) {
			String v = hash.search(i);
			if (("V" + i).equals(v)) {
				passed++;
			}
		}

		for (int i = 1; i <= n; i++) {
			if (hash.delete(i)) {
				passed++;
			}
		}

		boolean sizeCheck = hash.size() == 0;

		System.out.println("Operations passed: " + passed + "/" + total);
		System.out.println("Final size check (expect 0): " + hash.size());
		System.out.println(sizeCheck && passed == total ? "Result: PASS" : "Result: FAIL");
	}

	private static void testCollisionHandling() {
		System.out.println("\n2) Collision handling demonstration (separate chaining)");

		BasicHash<Integer, String> hash = new BasicHash<>(10);
		int[] collidingKeys = {1, 11, 21, 31, 41};

		for (int key : collidingKeys) {
			hash.insert(key, "C" + key);
		}

		boolean insertedAndFound = true;
		for (int key : collidingKeys) {
			String v = hash.search(key);
			if (!("C" + key).equals(v)) {
				insertedAndFound = false;
				break;
			}
		}

		boolean deleteMiddle = hash.delete(21);
		boolean chainStillValid = "C1".equals(hash.search(1))
				&& "C11".equals(hash.search(11))
				&& hash.search(21) == null
				&& "C31".equals(hash.search(31))
				&& "C41".equals(hash.search(41));

		System.out.println("Colliding keys used: 1, 11, 21, 31, 41 (all map to bucket 1 for capacity 10)");
		System.out.println("All colliding keys retrievable: " + insertedAndFound);
		System.out.println("Delete middle chain node (21) success: " + deleteMiddle);
		System.out.println("Remaining chain still correct: " + chainStillValid);
		System.out.println(insertedAndFound && deleteMiddle && chainStillValid ? "Result: PASS" : "Result: FAIL");
	}

	private static void testPerfectHashStaticDataset() {
		System.out.println("\n3) Perfect hash on static dataset (zero-collision construction)");

		int[] staticKeys = {
				7, 13, 29, 35, 42, 56, 68, 71,
				84, 95, 103, 117, 126, 134, 149, 158
		};

		PerfectHash perfect = new PerfectHash(staticKeys);
		boolean allFound = true;

		for (int key : staticKeys) {
			if (!perfect.search(key)) {
				allFound = false;
				break;
			}
		}

		System.out.println("Static dataset size: " + staticKeys.length);
		System.out.println("All static keys found after build: " + allFound);
		System.out.println("Result: " + (allFound ? "PASS" : "FAIL"));
		System.out.println("Note: This PerfectHash implementation rebuilds until placement has no bucket conflicts.");
	}

	private static void comparePerformance() {
		System.out.println("\n4) Performance comparison: BasicHash vs PerfectHash");

		int n = 5000;
		int[] keys = new int[n];
		for (int i = 0; i < n; i++) {
			keys[i] = i * 2 + 1;
		}

		BasicHash<Integer, Integer> basic = new BasicHash<>(10007);

		long t1 = System.nanoTime();
		for (int key : keys) {
			basic.insert(key, key);
		}
		long t2 = System.nanoTime();

		for (int key : keys) {
			basic.search(key);
		}
		long t3 = System.nanoTime();

		long t4 = System.nanoTime();
		PerfectHash perfect = new PerfectHash(keys);
		long t5 = System.nanoTime();

		for (int key : keys) {
			perfect.search(key);
		}
		long t6 = System.nanoTime();

		long basicInsertNs = t2 - t1;
		long basicSearchNs = t3 - t2;
		long perfectBuildNs = t5 - t4;
		long perfectSearchNs = t6 - t5;

		System.out.println("Dataset size: " + n);
		System.out.println("BasicHash insert time:  " + basicInsertNs + " ns");
		System.out.println("BasicHash search time:  " + basicSearchNs + " ns");
		System.out.println("PerfectHash build time: " + perfectBuildNs + " ns");
		System.out.println("PerfectHash search time: " + perfectSearchNs + " ns");
	}
}
