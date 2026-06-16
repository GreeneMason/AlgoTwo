package SAT2;



public class Main {
    public static void main(String[] args) {
        testFormula1();
        testFormula2();
        testFormula3();
    }

    // 1. A satisfiable formula (5 variables, 10 clauses)
    private static void testFormula1() {
        System.out.println("--- Test 1: Satisfiable Formula (5 vars, 10 clauses) ---");
        int n = 5;
        SAT2 solver = new SAT2(n);
        
        // A complex set of overlapping clauses designed to resolve cleanly
        int[][] clauses = {
            {1, 2}, {-1, 3}, {2, -4}, {3, 5}, {-2, 4},
            {-4, 5}, {-3, 1}, {5, -1}, {-5, 3}, {1, -4}
        };
        
        for (int[] c : clauses) solver.addClause(c[0], c[1]);
        evaluateAndVerify(solver, clauses, n);
    }

    // 2. An unsatisfiable formula
    private static void testFormula2() {
        System.out.println("--- Test 2: Unsatisfiable Formula ---");
        int n = 2;
        SAT2 solver = new SAT2(n);
        
        // A classic contradiction: x1 and x2 are forced into opposing states endlessly
        int[][] clauses = {
            {1, 2}, {-1, 2}, {1, -2}, {-1, -2}
        };
        
        for (int[] c : clauses) solver.addClause(c[0], c[1]);
        evaluateAndVerify(solver, clauses, n);
    }

    // 3. A formula where the solution is non-obvious
    private static void testFormula3() {
        System.out.println("--- Test 3: Non-obvious Solution ---");
        int n = 4;
        SAT2 solver = new SAT2(n);
        
        // This simulates an implication chain (x1 -> x2 -> x3 -> x4 -> -x1) 
        // This chain dictates x1 MUST be false. Clause (1 v 3) then forces x3 to be true.
        int[][] clauses = {
            {-1, 2}, {-2, 3}, {-3, 4}, {-4, -1}, {1, 3}
        };
        
        for (int[] c : clauses) solver.addClause(c[0], c[1]);
        evaluateAndVerify(solver, clauses, n);
    }

    // Utility to solve, print, and verify
    private static void evaluateAndVerify(SAT2 solver, int[][] clauses, int n) {
        boolean isSat = solver.solve();
        System.out.println("Is Satisfiable? : " + isSat);
        
        if (isSat) {
            boolean[] assignment = solver.getAssignment();
            
            // Print the assignment
            System.out.print("Valid Assignment: ");
            for (int i = 1; i <= n; i++) {
                System.out.print("x" + i + "=" + (assignment[i] ? "T " : "F "));
            }
            System.out.println();
            
            // Verify each clause
            boolean allSatisfied = true;
            for (int[] c : clauses) {
                boolean val1 = c[0] > 0 ? assignment[c[0]] : !assignment[-c[0]];
                boolean val2 = c[1] > 0 ? assignment[c[1]] : !assignment[-c[1]];
                
                if (!(val1 || val2)) {
                    allSatisfied = false;
                    System.out.println(">> FAILED on clause: (" + c[0] + " v " + c[1] + ")");
                }
            }
            System.out.println("Verification    : " + (allSatisfied ? "PASSED - All clauses evaluate to True." : "FAILED"));
        }
        System.out.println();
    }
}

