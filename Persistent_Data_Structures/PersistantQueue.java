
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
public class PersistantQueue<T> {

    private PersistantStack<T> back;
    private PersistantStack<T> front;

    public PersistantQueue() {
        back = new PersistantStack<>();
        front = new PersistantStack<>();
    }

    public PersistantQueue(PersistantStack<T> back, PersistantStack<T> front) {
        this.back = new PersistantStack<>(back);
        this.front = new PersistantStack<>(front);
    }

    public PersistantQueue(PersistantQueue<T> other) {
        this.back = new PersistantStack<>(other.back);
        this.front = new PersistantStack<>(other.front);
    }

    PersistantQueue push(T value) {
        return new PersistantQueue<>(back.push(value), new PersistantStack<>(front));
    }

    PersistantQueue pop() {
        if (front.isEmpty()) {
            if (back.isEmpty()) {
                throw new EmptyStackException();
            }
            return new PersistantQueue<>(new PersistantStack<>(), back.reverse().pop());
        }
        return new PersistantQueue<>(new PersistantStack<>(back), front.pop());
    }

    T front() {
        if (front.isEmpty()) {
            if (back.isEmpty()) {
                throw new EmptyStackException();
            }
            front = back.reverse();
            back = new PersistantStack<>();
        }
        return front.top();
    }

}
