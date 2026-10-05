import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    /**
     * A node in the circular doubly-linked list.
     * The sentinel node has a null item.
     */
    class Node {
        private final T item;
        private Node prev, next;

        private Node (T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /*
     * Invariant:
     * - sentinel.next is the first real node, or sentinel if empty.
     * - sentinel.prev is the last real node, or sentinel if empty.
     * - sentinel.item is always null.
     */
    private final Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node first = sentinel.next;
        Node itemToAdd = new Node(x, sentinel, first);
        sentinel.next = itemToAdd;
        first.prev = itemToAdd;

        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node last = sentinel.prev;
        Node itemToAdd = new Node(x, last, sentinel);
        last.next = itemToAdd;
        sentinel.prev = itemToAdd;

        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            result.add(current.item);
            current = current.next;
        }
        return result;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        return sentinel.next.item;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        return sentinel.prev.item;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node first = sentinel.next;
        Node newFirst = first.next;

        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        size--;

        first.prev = null;
        first.next = null;
        return first.item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node last = sentinel.prev;
        Node newLast = last.prev;

        sentinel.prev = newLast;
        newLast.next = sentinel;
        size--;

        last.prev = null;
        last.next = null;
        return last.item;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node current) {
        if (index == 0) {
            return current.item;
        }
        return getRecursiveHelper(index - 1, current.next);
    }

}
