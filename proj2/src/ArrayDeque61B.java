import org.jspecify.annotations.NonNull;

import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int capacity = 8;
    private T[] items;
    private int size;
    private int first = 0, last = 0;

    private int getPrev(int first) {
        if (first == 0) {
            return capacity - 1;
        }
        return first - 1;
    }

    private int getNext(int last) {
        if (last == capacity - 1) {
            return 0;
        }
        return last + 1;
    }

    public ArrayDeque61B() {
        size = 0;
        items = (T[]) new Object[capacity];
    }


    /**
     * Enlarge the capacity by duplicate the original one into larger one.
     */
    private void resizeUp() {
        T[] newItems = (T[]) new Object[capacity * 2];
        int current = first;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = getNext(current);
        }

        items = newItems;
        first = 0;
        last = size;
        capacity *= 2;
    }

    /**
     * Shrink the capacity by duplicate the original one into smaller one.
     */
    private void resizeDown() {
        T[] newItems = (T[]) new Object[capacity / 2];
        int current = first;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = getNext(current);
        }

        items = newItems;
        first = 0;
        last = size;
        capacity /= 2;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resizeUp();
        }
        size += 1;

        first = getPrev(first);
        items[first] = x;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resizeUp();
        }
        size += 1;

        items[last] = x;
        last = getNext(last);
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        int current = first;
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(items[current]);
            current = getNext(current);
        }
        return returnList;
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
        if (size == 0) {
            return null;
        }
        return items[first];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (size == 0) {
            return null;
        }
        return items[getPrev(last)];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        size -= 1;

        T removedFirst = items[first];
        items[first] = null;
        first = getNext(first);
        if(size <= capacity / 4) {
            resizeDown();
        }

        return removedFirst;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        size -= 1;

        last = getPrev(last);
        T removedLast = items[last];
        items[last] = null;
        if(size <= capacity / 4) {
            resizeDown();
        }

        return removedLast;
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

        int current = first;
        for (int i = 0; i < index; i++) {
            current = getNext(current);
        }
        return items[current];
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
        return getRecursiveHelper(index, first);
    }

    private T getRecursiveHelper(int index, int current) {
        if (index == 0) {
            return items[current];
        }
        return getRecursiveHelper(index - 1, getNext(current));
    }

    private class ArrayDequeIterator implements Iterator<T> {

        int current, counts;
        public ArrayDequeIterator() {
            current = first;
            counts = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return counts < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T pickedElement = items[current];
            current = getNext(current);
            counts += 1;
            return pickedElement;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public @NonNull Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof ArrayDeque61B<?> counterpart)) {
            return false;
        }

        Iterator<T> itSelf = iterator();
        Iterator<?> itOther = counterpart.iterator();

        while (itSelf.hasNext() && itOther.hasNext()) {
            if (!Objects.equals(itSelf.next(), itOther.next())) {
                return false;
            }
        }
        return !itSelf.hasNext() && !itOther.hasNext();
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();

        String result = "[";
        while (it.hasNext()) {
            result += it.next();
            if (it.hasNext()) { result += ", "; }
        }
        result += "]";
        return result;
    }
}
