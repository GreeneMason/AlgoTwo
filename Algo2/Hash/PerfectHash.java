package Algo2.Hash;
import java.util.Random;
public class PerfectHash {
    private Integer[] table;
    private int[] staticKeys;
    private int size; 
    private long prime = 1000000007L;
    private long x = 0;
    private long y = 0;
    private Random random;

    public PerfectHash(int[] keys) {
        this.staticKeys = keys;
        int n = staticKeys.length;
        this.size = n * n; 
        this.table = new Integer[size];
        this.random = new Random();

        hashPerfectHash();
    }

    private int hash(int key) {
        long hashVal = ((x * key + y) % prime) % size;
        return (int) hashVal;
    }

    private void hashPerfectHash() {
        boolean perfect = false;
        while (!perfect) {
            x = random.nextInt((int) prime);
            y = random.nextInt((int) prime);
            perfect = true;
        
            for (int j = 0; j < staticKeys.length; j++) {
                int key = staticKeys[j]; 
                int index = hash(key);
                if (table[index] != null) {
                    perfect = false;
                    for (int i = 0; i < size; i++) {
                        table[i] = null;
                    }
                    break;
                }
                table[index] = key;
            }
        }
    }

    public boolean search(int key) {
        int index = hash(key);
        return table[index] != null && table[index] == key;
    }
}



