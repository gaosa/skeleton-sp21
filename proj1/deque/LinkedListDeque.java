package deque;

public class LinkedListDeque<T> {

    public class LinkedListNode<T> {
        // class variables of the LinkedListNode class
        public LinkedListNode prev;
        public T item;
        public LinkedListNode next;
        // constructor
        public LinkedListNode(LinkedListNode p, T i, LinkedListNode n) {
            prev = p;
            item = i;
            next = n;
        }
        // together to define the LinkedListNode class
    }

    // class variables of the LinkedListDeque class
    public LinkedListNode sentinel;
    public int size = 0;
    // constructor
    public LinkedListDeque() {
        sentinel = new LinkedListNode(null, 0, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    // together to define the LinkedListDeque class

    /** Adds an item of type T to the front of the deque. You can assume that item is never null. */
    public void addFirst(T item) {
        LinkedListNode node = new LinkedListNode(sentinel, item, sentinel.next);
        node.prev = sentinel;
        node.next = sentinel.next;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. You can assume that item is never null. */
    public void addLast(T item) {
        LinkedListNode node = new LinkedListNode(sentinel.prev, item, sentinel);
        sentinel.prev.next = node;
        node.next = sentinel;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        } else {
            return false;
        }
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(sentinel.next.item);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        LinkedListNode first = sentinel.next;
        if (first != sentinel) {
            sentinel.next = sentinel.next.next;
            return first.item;
        } else {
            return null;
        }
        size -= 1;

    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        LinkedListNode last = sentinel.prev;
        if (last != sentinel) {
            sentinel.prev = sentinel.prev.prev;
            return last.item;
        } else {
            return null;
        }
        size -= 1;

    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        LinkedListNode curr = sentinel;
        for (int i = 0; i <= index; i += 1) {
            curr = curr.next;
        }
        return curr.item;
    }

}
