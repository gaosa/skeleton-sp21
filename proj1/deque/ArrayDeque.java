package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {

    public T[] items;
    public int size;
    public int firstIdx;
    public int lastIdx;
    public static int RUFACTOR = 2;
    public static double RDFACTOR = 0.5;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        firstIdx = 0;
        lastIdx = 0;
    }

    private void resize(int capacity) {
        T[] dequeCopy = (T[]) new Object[capacity];
        if (lastIdx >= firstIdx) {
            System.arraycopy(items, firstIdx, dequeCopy, 0, lastIdx - firstIdx + 1);
        } else {
            System.arraycopy(items, firstIdx, dequeCopy, 0, items.length - firstIdx);
            System.arraycopy(items, 0, dequeCopy, items.length - firstIdx, lastIdx + 1);
        }
        items = dequeCopy;
        firstIdx = 0;
        lastIdx = size - 1;
//        lastIdx = Math.max(0, size - 1);
    }

    private int mod(int idx) {
        return (idx + items.length) % items.length;
    }

    /** Adds an item of type T to the front of the deque. You can assume that item is never null. */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * RUFACTOR);
        }
        if (size > 0) {
            firstIdx = mod(firstIdx - 1);
        }
        // firstIdx = firstIdx > 0 ? firstIdx - 1 : items.length - 1;
        items[firstIdx] = item;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. You can assume that item is never null. */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * RUFACTOR);
        }
        if (size > 0) {
            lastIdx = mod(lastIdx + 1);
        }
        // lastIdx = lastIdx < items.length - 1 ? lastIdx + 1 : 0;
//        System.out.println("lastIdx: " + lastIdx);
        items[lastIdx] = item;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
//    public boolean isEmpty() {
//        return (size == 0);
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line. */
    @Override
    public void printDeque() {
        for (int i = firstIdx, j = size; j > 0; i = mod(i + 1), j -= 1) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size < 0.25 * items.length) {
            resize((int) Math.ceil(items.length * RDFACTOR));
        }
        T first = items[firstIdx];
        items[firstIdx] = null;
        // firstIdx = firstIdx < size - 1 ? firstIdx + 1 : 0;
        size -= 1;
        if (size > 0) {
            firstIdx = mod(firstIdx + 1);
        }
        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (items.length >= 16 && size < 0.25 * items.length) {
            resize((int) Math.ceil(items.length * RDFACTOR));
        }
        T last = items[lastIdx];
        items[lastIdx] = null;
        // lastIdx = lastIdx > 0 ? lastIdx - 1 : size - 1;
//        System.out.println("lastIdx: " + lastIdx);
        size -= 1;
        if (size > 0) {
            lastIdx = mod(lastIdx - 1);
        }
//        System.out.println("lastIdx: " + lastIdx);
        return last;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int idx = mod(firstIdx + index);
        return items[idx];
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator. */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;
        private ArrayDequeIterator() {
            wizPos = firstIdx;
        }

        @Override
        public boolean hasNext() {
            return wizPos != lastIdx;
        }

        @Override
        public T next() {
            T itemReturn = items[wizPos];
            while (hasNext()) {
                wizPos = mod(wizPos + 1);
            }
            return itemReturn;
        }
    }

    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this.
     * Read here for more information) */
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        ArrayDeque<T> oDeque =  (ArrayDeque<T>) o;
        if (this.size != oDeque.size) {
            return false;
        }
        Iterator<T> aDequeSeer = this.iterator();
        Iterator<T> oSeer = oDeque.iterator();
        while (aDequeSeer.hasNext()) {
            if (!aDequeSeer.next().equals(oSeer.next())) {
                return false;
            }
        }
        return true;
    }
}
