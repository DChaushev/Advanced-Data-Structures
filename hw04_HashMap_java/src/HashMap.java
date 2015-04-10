
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Simple HashMap implementation in Java
 *
 * @author: Dimitar
 * @keywords: Data Structures, Map, Hashing
 * @modified:
 *
 * Implement an unordered map with String keys. Use load factor ((number of
 * elements + 1) / number of buckets) of at least 0.25.
 *
 * (optional): Make it also work for integers (int) as keys. (optional): Make it
 * also work for arbitrary objects as keys.
 */
public class HashMap<V> {

    private final float LOAD_FACTOR = 0.75f;
    private long maxElementsAlowed;
    private List<MapEntry> buckets;

    public HashMap() {
        maxElementsAlowed = 1 << 4;
        buckets = new ArrayList<>(1 << 4);
    }

    private class MapEntry {

        final int hash;
        String key;
        V value;

        public MapEntry(int hash, String key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public int getHash() {
            return this.hash;
        }
    }

    /**
     * Resizes the HashMap so it has at least numBuckets slots (buckets).
     * Re-hashes the current values if needed. Expected time complexity: O(N +
     * numBuckets)
     */
    public void resize(int numBuckets) {
        // TODO: implement
    }

    /**
     * Removes all existing values from the HashMap and leaves it empty.
     * Expected complexity: O(N + B)
     */
    public void clear() {
        // TODO: implement
    }

    /**
     * Returns the number of elements in the HashMap. Expected complexity: O(1)
     */
    public int size() {
        // TODO: implement
        return -1;
    }

    /**
     * Returns the number of allocated buckets in the HashMap. Expected
     * complexity: O(1)
     */
    public int capacity() {
        // TODO: implement
        return -1;
    }

    /**
     * Checks if a value is already associated with a certain key. Expected
     * complexity: O(H + 1), where O(H) is needed to hash the key.
     */
    public boolean contains(String key) {
        // TODO: implement
        return false;
    }

    /**
     * Inserts a new (key, value) pair or replaces the current value with
     * another one, if the key is already present. Expected complexity: O(H +
     * 1), where O(H) is needed to hash the key.
     */
    public void insert(String key, V value) {
        // TODO: implement
    }

    /**
     * Removes a key and the associated with it value from the HashMap. Expected
     * complexity: O(H + 1), where O(H) is needed to hash the key.
     */
    public void erase(String key) {
        // TODO: implement
    }

    /**
     * Returns a reference to the value, associated with a certain key. If the
     * key is not present in the HashMap, throw an error or fail by assertion.
     * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
     */
    public V get(String key) {
        // TODO: implement
        return null;
    }
}
