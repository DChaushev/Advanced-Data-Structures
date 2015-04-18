
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Simple HashMap implementation in Java
 *
 * @author: Dimitar
 * @param <V>
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

    private final int BASE = 127;
    private final double LOAD_FACTOR = 0.75;
    private final int MIN_BINARY_POWER = 4;

    private int MOD;
    private int capacity;
    private int numberOfElements;
    private List<MapEntry>[] buckets;
    private int binaryPower = MIN_BINARY_POWER;

    public HashMap() {
        init(1 << binaryPower);
    }

    private class MapEntry {

        private final int hash;
        private final String key;
        private V value;

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

        @Override
        public boolean equals(Object obj) {
            MapEntry other = (MapEntry) obj;
            return this.key.equals(other.key);
        }
    }

    /**
     *
     * This method takes the minimum number of buckets required and initializes
     * the HashMap's:
     *
     * - MOD: it is initialized with the smallest prime, bigger than the number
     * of buckets required.
     *
     * - capacity: I really wanted the capacity to be a number to the power of
     * two, but and the MOD to be the next prime, but thus the generated hash
     * might be<br/>
     * capacity < hash < MOD </br>, which will cause problems, so I had to
     * choose MOD = capacity = next_prime or MOD = capacity = (2^x)
     *
     * I chose the first one. So the capacity = MOD = next_prime
     *
     * - buckets is a array of linked lists
     *
     * - numberOfElements at the beginning is 0
     *
     * @param numBuckets
     */
    private void init(int numBuckets) {
        MOD = getNextPrime(numBuckets);
        capacity = MOD;
        buckets = new List[MOD + 1];
        numberOfElements = 0;
    }

    /**
     *
     * Generates hash by a given string.
     *
     * The hash is in the interval between 0 and MOD.
     *
     * @param str
     * @return
     */
    private int generateHash(String str) {
        int ret = 1;
        for (int i = 0; i < (int) str.length(); i++) {
            ret = (int) (((long) ret * BASE + (int) str.charAt(i))) % MOD;
        }
        return ret;
    }

    /**
     * Checks if a number is prime. I use this function when searching for the
     * next prime.
     *
     * @param n
     * @return
     */
    private boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the smallest prime number, bigger than a given number n.
     *
     * @param n
     * @return
     */
    private int getNextPrime(int n) {
        int i = n;
        for (; i < 2 * n; ++i) {
            if (isPrime(i)) {
                break;
            }
        }
        return i;
    }

    /**
     *
     * By given bucket and key, it searches the bucket for an element with this
     * key and returns it if there is one. Returns null otherwise.
     *
     * @param bucket
     * @param key
     * @return
     */
    private MapEntry getEntryFromBucket(List<MapEntry> bucket, String key) {
        for (MapEntry element : bucket) {
            if (element.getKey().equals(key)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Resizes the HashMap so it has at least numBuckets slots (buckets).
     * Re-hashes the current values if needed. Expected time complexity: O(N +
     * numBuckets)
     *
     * @param numBuckets
     */
    public void resize(int numBuckets) {

        List<MapEntry> entries = new ArrayList<>(numberOfElements);

        for (List<MapEntry> bucket : buckets) {
            if (bucket != null) {
                bucket.stream().forEach((me) -> {
                    entries.add(me);
                });
            }
        }
        init(numBuckets);
        entries.forEach(e -> this.insert(e.key, e.value));
    }

    /**
     * Removes all existing values from the HashMap and leaves it empty.
     *
     * Also - returns it to it's initial state.
     *
     * Expected complexity: O(N + B)
     */
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i].clear();
                buckets[i] = null;
            }
        }
        binaryPower = MIN_BINARY_POWER;
        init(1 << binaryPower);
    }

    /**
     * Returns the number of elements in the HashMap. Expected complexity: O(1)
     *
     * @return
     */
    public int size() {
        return this.numberOfElements;
    }

    /**
     * Returns the number of allocated buckets in the HashMap. Expected
     * complexity: O(1)
     *
     * @return
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     * Checks if a value is already associated with a certain key. Expected
     * complexity: O(H + 1), where O(H) is needed to hash the key.
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        int hash = generateHash(key);
        if (buckets[hash] == null) {
            return false;
        } else {
            return buckets[hash].stream().anyMatch((el) -> (el.getKey().equals(key)));
        }
    }

    /**
     * Inserts a new (key, value) pair or replaces the current value with
     * another one, if the key is already present. Expected complexity: O(H +
     * 1), where O(H) is needed to hash the key.
     *
     * After the insertions, the load factors is checked and the hashmap is
     * expanded if there's need for it.
     *
     * @param key
     * @param value
     */
    public void insert(String key, V value) {
        int hash = generateHash(key);
        if (buckets[hash] == null) {
            buckets[hash] = new LinkedList<>();
            buckets[hash].add(new MapEntry(hash, key, value));
            numberOfElements++;
        } else {
            for (MapEntry el : buckets[hash]) {
                if (el.getKey().equals(key)) {
                    el.setValue(value);
                    return;
                }
            }
            buckets[hash].add(new MapEntry(hash, key, value));
            numberOfElements++;
        }

        if ((double) numberOfElements / (double) buckets.length >= LOAD_FACTOR) {
            resize(1 << ++binaryPower);
        }
    }

    /**
     * Removes a key and the associated with it value from the HashMap. Expected
     * complexity: O(H + 1), where O(H) is needed to hash the key.
     *
     * After the deletion, the load factors is checked and the hashmap shrinks
     * if there's need for it.
     *
     * @param key
     */
    public void erase(String key) {
        int hash = generateHash(key);
        MapEntry toBeDeleted;
        if (buckets[hash] != null) {
            if ((toBeDeleted = getEntryFromBucket(buckets[hash], key)) != null) {
                buckets[hash].remove(toBeDeleted);
                numberOfElements--;
            }
        }

        if (binaryPower > MIN_BINARY_POWER && (double) numberOfElements / (double) buckets.length <= 1 - LOAD_FACTOR) {
            resize(1 << --binaryPower);
        }
    }

    /**
     * Returns a reference to the value, associated with a certain key. If the
     * key is not present in the HashMap, throw an error or fail by assertion.
     * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
     *
     * @param key
     * @return
     */
    public V get(String key) {
        int hash = generateHash(key);
        MapEntry me;
        if (buckets[hash] != null) {
            if ((me = getEntryFromBucket(buckets[hash], key)) != null) {
                return me.value;
            }
        }
        throw new NoSuchElementException("The key '" + key + "' is not present the hashmap");
    }

    /**
     * This method displays all the elements from the hashmap.
     */
    public void treverse() {
        for (List<MapEntry> bucket : buckets) {
            if (bucket != null) {
                bucket.stream().filter((element) -> (element != null)).forEach((element) -> {
                    System.out.print("[" + element.getKey() + ": " + element.getHash() + "] ");
                });
                System.out.println();
            }
        }
    }
}
