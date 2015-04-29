
import java.util.EmptyStackException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author grade
 */
public class PersistantStack<T> {

    private Node top;

    public PersistantStack() {
        top = null;
    }

    public PersistantStack(PersistantStack<T> other) {
        this.top = other.top;
    }

    public PersistantStack push(T value) {
        PersistantStack result = new PersistantStack(this);

        Node newNode = new Node(value);

        if (result.top == null) {
            result.top = newNode;
            return result;
        } else {
            newNode.next = result.top;
            result.top = newNode;
            return result;
        }
    }

    public PersistantStack pop() {

        if (top == null) {
            throw new EmptyStackException();
        }

        PersistantStack result = new PersistantStack(this);

        result.top = result.top.next;

        return result;

    }

    public T top() {
        if (top == null) {
            throw new EmptyStackException();
        }
        return top.value;
    }

    PersistantStack reverse() {
        if (top == null) {
            return this;
        }

        PersistantStack original = new PersistantStack(this);
        PersistantStack<T> result = new PersistantStack<>();
        
        while(original.top != null){
            result = result.push((T) original.top.value);
            original = original.pop();
        }
        
        return result;
    }

    public boolean isEmpty() {
        return top == null;
    }

    private class Node {

        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
        
        public Node(Node other){
            this.value = other.value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

    }

}
