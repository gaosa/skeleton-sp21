package deque;

public class ArrayDeque<T> {

    public T[] items;
    public int size;
    public int firstIdx;
    public int lastIdx;
    public static int RUFACTOR = 2;
    public static int RDFACTOR = 1/2;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        firstIdx = 0;
        lastIdx = 1;
    }

    private void resize(int capacity) {
        T[] DequeCopy = (T[]) new Object[capacity];
        if (lastIdx >= firstIdx) {
            System.arraycopy(items, firstIdx, DequeCopy, 0, lastIdx - firstIdx + 1);
        } else {
            System.arraycopy(items, firstIdx, DequeCopy, 0, size - firstIdx);
            System.arraycopy(items, 0, DequeCopy, size - firstIdx, lastIdx - 1);
        }
        items = DequeCopy;
    }

    /** Adds an item of type T to the front of the deque. You can assume that item is never null. */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * RUFACTOR);
            firstIdx = 0;
            lastIdx = size - 1;
        }
        items[firstIdx] = item;
        firstIdx = firstIdx > 0 ? firstIdx - 1 : size - 1;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. You can assume that item is never null. */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * RUFACTOR);
            firstIdx = 0;
            lastIdx = size - 1;
        }
        items[lastIdx] = item;
        lastIdx = lastIdx < size - 1 ? lastIdx + 1 : 0;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size < 0.25 * items.length) {
            resize(size * RDFACTOR);
            firstIdx = 0;
            lastIdx = size - 1;
        }
        T first = items[firstIdx];
        items[firstIdx] = null;
        firstIdx = firstIdx < size - 1 ? firstIdx + 1 : 0;
        size -= 1;

        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size < 0.25 * items.length) {
            resize(size * RDFACTOR);
            firstIdx = 0;
            lastIdx = size - 1;
        }
        T last = items[lastIdx];
        items[lastIdx] = null;
        lastIdx = lastIdx > 0 ? lastIdx - 1 : size - 1;
        size -= 1;
        return last;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[index];
    }
}
