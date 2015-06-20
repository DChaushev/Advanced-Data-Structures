/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binomial_heap;

/**
 *
 * @author Dimitar
 */
public interface BinomialHeapInterface {

    /**
     * The root of the heap. Should be kept NULL all the time the heap is empty
     *
     * As this is interface we don't actually declare the field here. We expect
     * it to happen in the implementation.
     */
    //private Node root;
    /**
     * The number of elements currently stored in the heap.
     *
     * As this is interface we don't actually declare the field here. We expect
     * it to happen in the implementation.
     */
    //private int size;
    public static class Node {

        // TODO: remove public modifiers!!!
        public Node right;
        Node parent;
        Node leftmostChild;
        Comparable key;

        public Node(Comparable key) {
            this.key = key;
        }

    }

    /**
     * Construct empty binomial heap. As this is abstract class it is
     *
     * As this is interface we don't actually declare the constructor here. We
     * expect it to happen in the implementation.
     */
    //public BinomialHeap();
    /**
     * Construct heap with the given elements. Expected runneing time
     * O(elements.length)
     *
     * @param elements - array containing the elements
     *
     * As this is interface we don't actually declare the constructor here. We
     * expect it to happen in the implementation.
     */
    //public BinomialHeap(Comparable [] elements);
    /**
     * Auxiliary function to be used when uniting two heaps.
     *
     * This function should merge the two given root lists in the root list of a
     * single binomial heap - the result of the merging.
     *
     *
     * |NOTE| This method could have been static as long as it will not access
     * any |NOTE| of the fields of the class. Still it is not static, because in
     * some |NOTE| of the tests it will be overridden.
     *
     * @param leftRoot - the first node of the root list of the first heap
     * @param leftSize - the number of the elements in the left heap
     * @param rightRoot - the first node of the root list of the second heap
     * @param rightSize - the number of the elements in the right heap
     * @return the first node of the root list of the binomial heap result of
     * the merging of the two given heaps
     */
    Node consolidate(Node leftRoot, int leftSize, Node rightRoot, int rightSize);

    /**
     * @return - the key of the minimal element currently stored in the heap
     */
    public Comparable getMin();

    /**
     * @return - the root of the heap. Will be used for testing purposes.
     */
    public Node getRoot();

    /**
     * @return - the number of the elements currently stored in the heap.
     */
    public int getSize();

    /**
     * Extracts (removes) the element with minimal key from the heap
     *
     * @return - the key of the minimal element currently stored in the heap
     */
    public Comparable extractMin();

    /**
     * Unites the current heap with the one given. It is expected that the
     * tructure of otherHeap will be destroyed in this method.
     *
     * This method is virtual for ease of testing. Still you need to implement
     * it.
     *
     * @param otherHeap - the heap with which we will unite. It will be
     * destroyed in this method
     */
    public void union(BinomialHeapInterface otherHeap);

    /**
     * Inserts element with the new given key in the heap.
     *
     * @param newKey - the key of the element to be inserted
     * @return a pointer to the newly created element in the heap
     */
    public Node push(Comparable newKey);

    /**
     * Decreases the key of the given element to the given amount. It is
     * guaranteed the ptr is real pointer to element of the heap. WARNING!!! No
     * changes should be made if the newly given key is larger than the current
     * value of the key. WARNING!!!
     *
     * @param node - a pointer to the element which key is to be modified
     * @param newKey - the new value of the key to be used for element pointed
     * by ptr
     */
    public void decreaseKey(Node node, Comparable newKey);
}
