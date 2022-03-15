package deque;

import java.util.Iterator;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);

    default boolean isEmpty() {
        return size() == 0;
    }

    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);

    default boolean equals(Iterable<T> it, Object o) {
        if (o == null) {
            System.out.println("1");
            return false;
        }
        if (!(o instanceof Iterable)) {
            System.out.println("2");
            return false;
        }
        Iterator<T> i1 =  it.iterator();
        Iterator<T> i2 = ((Iterable<T>) o).iterator();
        while (i1.hasNext() && i2.hasNext()) {
            final T t1 = i1.next(), t2 = i2.next();
            if (!t1.equals(t2)) {
                return false;
            }
        }
        return !i1.hasNext() && !i2.hasNext();
    }
}
