/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author grade
 */
public interface SplayTreeInterface {
    
    public void insert(int key);
    public void delete(int key);
    public boolean contains(int key);
    
    class Node{
        public Node left;
        public Node right;
        public Node parent;
        public int key;

        public Node(int key) {
            this.key = key;
        }
        
        
    }
    
}
