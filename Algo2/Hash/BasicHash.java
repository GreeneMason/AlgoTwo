package Algo2.Hash;
class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next;

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

public class BasicHash <K, V>{
    private HashNode<K, V>[] table;
    private int capacity;
    private int size;
    
    public BasicHash(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
        this.size = 0;
    }

    private int getHash(K key) {
        // Use Java's native hashCode, ensure positive index
        return Math.abs(key.hashCode()) % capacity;
    }
    //insert a key-value pair into the hash table
    public void insert(K key, V value) {
        int index = getHash(key);
        HashNode<K, V> current = table[index];

        // If bucket is empty, create new node
        if (current == null) {
            table[index] = new HashNode<>(key, value);
            size++;
            return;
        }

        // To find the key
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value; // Update value if key exists
                return;
            }
            if (current.next == null) break;
            current = current.next;
        }

        // Add new node to chain
        current.next = new HashNode<>(key, value);
        size++;
    }
    //search for a value by key in the hash table
    public V search(K key) {
        int index = getHash(key);
        HashNode<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    //delete a key-value pair from the hash table
    public boolean delete(K key) {
        int index = getHash(key);
        HashNode<K, V> current = table[index];
        HashNode<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    //Size of hash table
    public int size() {
        return size;
    }
}
