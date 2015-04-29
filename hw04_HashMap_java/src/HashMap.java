
import java.util.ArrayList;
import java.util.Collections;
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
 * (optional): Make it also work for integers (int) as keys.
 *
 * (optional): Make it also work for arbitrary objects as keys.
 */
public class HashMap<V> {

    private final int BASE = 127;
    private final double LOAD_FACTOR = 0.75;
    private final int MIN_BINARY_POWER;
    private final int NEEDLESS = -1;

    private final int MIN_BUCKETS;
    private final int MAX_BUCKETS;

    private int MOD;
    private int capacity;
    private int numberOfElements;
    private List<MapEntry> buckets;
    private int binaryPower;

    /**
     * Constructs an empty HashMap. Expected complexity: O(1)
     */
    public HashMap() {
        binaryPower = MIN_BINARY_POWER = 2;
        MIN_BUCKETS = 1 << binaryPower;
        init(MIN_BUCKETS);

        MAX_BUCKETS = NEEDLESS;
    }

    /**
     * Constructs an empty HashMap with minimum minBuckets buckets. This lower
     * bound on the number of buckets should be maintained at all times!
     * Expected complexity: O(minBuckets)
     */
    HashMap(int minBuckets) {
        binaryPower = MIN_BINARY_POWER = getPowerOfTwo(minBuckets);
        MIN_BUCKETS = minBuckets;
        init(minBuckets);

        MAX_BUCKETS = NEEDLESS;
    }

    /**
     * Constructs an empty HashMap with minimum minBuckets buckets and maximum
     * maxBuckets buckets. These lower and upper bounds on the number of buckets
     * should be maintained at all times! Expected complexity: O(minBuckets)
     */
    HashMap(int minBuckets, int maxBuckets) {
        binaryPower = MIN_BINARY_POWER = getPowerOfTwo(minBuckets);
        MIN_BUCKETS = minBuckets;
        init(1 << binaryPower);

        MAX_BUCKETS = maxBuckets;
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
     * capacity < hash < MOD </br>
     *
     * which will cause problems, so I had to choose MOD = capacity = next_prime
     * or MOD = capacity = (2^x)
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
        capacity = numBuckets;
        MOD = getNextPrime(capacity);
        buckets = new ArrayList<>(Collections.nCopies(capacity, null));
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
        int h = str.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        h = h ^ (h >>> 7) ^ (h >>> 4);
        return Math.abs(h) % capacity;
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
     * Returns the power, by which two becomes >= n
     *
     * Example:
     *
     * getPowerOfTwo(32) == getPowerOfTwo(40) == 5
     *
     * getPowerOfTwo(128) == getPowerOfTwo(150) == 7
     *
     * @param n
     * @return
     */
    private int getPowerOfTwo(int n) {
        return (int) (Math.log(n) / Math.log(2));
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
    private MapEntry getEntryFromBucket(MapEntry bucket, String key) {
        while (bucket != null) {
            if (bucket.getKey().equals(key)) {
                return bucket;
            }
            bucket = bucket.getNext();
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
    private void resize(int numBuckets) {
        List<MapEntry> oldBuckets = buckets;

        init(numBuckets);

        for (MapEntry bucket : oldBuckets) {
            while (bucket != null) {
                this.insert(bucket.key, bucket.value);
                bucket = bucket.getNext();
            }
        }

    }

    /**
     * Removes all existing values from the HashMap and leaves it empty.
     *
     * Also - returns it to it's initial state.
     *
     * Expected complexity: O(N + B)
     */
    public void clear() {
        buckets.clear();
        if (MIN_BUCKETS <= (1 << MIN_BINARY_POWER)) {
            init(1 << MIN_BINARY_POWER);
        } else {
            init(MIN_BUCKETS);
        }
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
        if (buckets.get(hash) == null) {
            return false;
        } else {
            MapEntry bucket = getEntryFromBucket(buckets.get(hash), key);
            return bucket != null;
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
        if (buckets.get(hash) == null) {
            buckets.set(hash, new MapEntry(key, value));
            numberOfElements++;
        } else {
            MapEntry bucket = getEntryFromBucket(buckets.get(hash), key);
            if (bucket != null) {
                bucket.setValue(value);
                return;
            }

            MapEntry newBucket = new MapEntry(key, value);
            newBucket.setNext(buckets.get(hash));
            buckets.set(hash, newBucket);
            numberOfElements++;
        }

        if ((double) numberOfElements / (double) capacity >= LOAD_FACTOR) {
            if (MAX_BUCKETS != NEEDLESS) {
                if (capacity != MAX_BUCKETS) {
                    if (MAX_BUCKETS >= (1 << (binaryPower + 1))) {
                        resize(1 << ++binaryPower);
                    } else {
                        resize(MAX_BUCKETS);
                    }
                }
            } else {
                resize(1 << ++binaryPower);
            }
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
        if (buckets.get(hash) == null) {
            return;
        }

        MapEntry current = buckets.get(hash);
        MapEntry previous = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (previous != null) {
                    previous.setNext(current.getNext());
                } else {
                    buckets.set(hash, current.getNext());
                }
                numberOfElements--;
            }
            previous = current;
            current = current.getNext();
        }

        if (binaryPower - 1 >= MIN_BINARY_POWER && (double) numberOfElements / (double) capacity <= 1 - LOAD_FACTOR) {
            if (MIN_BUCKETS >= (1 << binaryPower - 1)) {
                resize(MIN_BUCKETS);
            } else {
                resize(1 << --binaryPower);
            }
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
        if (buckets.get(hash) != null) {
            if ((me = getEntryFromBucket(buckets.get(hash), key)) != null) {
                return me.getValue();
            }
        }
        throw new NoSuchElementException("The key '" + key + "' is not present the hashmap");
    }

    /**
     * This is the data structure I use to represent each of the buckets. It is
     * also a linked list to deal with the collisions.
     */
    private class MapEntry {

        private final String key;
        private V value;
        private MapEntry next;

        public MapEntry(String key, V value) {
            this.key = key;
            this.value = value;
            next = null;
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

        public MapEntry getNext() {
            return next;
        }

        public void setNext(MapEntry next) {
            this.next = next;
        }

    }

    /**
     * This method displays all the elements from the hashmap.
     */
    public void treverse() {
        int i = 0;
        for (MapEntry bucket : buckets) {
            if (bucket != null) {
                System.out.print(i + ": ");
                while (bucket != null) {
                    System.out.print("[" + bucket.getKey() + "] ");
                    bucket = bucket.getNext();
                }
                System.out.println();
            }
            i++;
        }
        System.out.println();
    }
}
