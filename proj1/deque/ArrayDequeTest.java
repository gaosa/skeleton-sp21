package deque;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> lld1 = new ArrayDeque<>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String>  lld1 = new ArrayDeque<>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void randomizedTesting() {
        // generate random numbers
        Random random = new Random();
        Deque<Integer> ref = new java.util.ArrayDeque<>();
        LinkedListDeque<Integer> myLLDeque = new LinkedListDeque<>();
        ArrayDeque<Integer> myADeque = new ArrayDeque<>();

        for (int i = 0; i < 10000; i += 1) {
            int rn = random.nextInt(4);
            int v = random.nextInt();
            switch (rn) {
                case 0 -> { // test addLast
                    System.out.println("addLast: " + v);
                    ref.addLast(v);
                    myLLDeque.addLast(v);
                    myADeque.addLast(v);
                }
                case 1 -> { // test addFirst;
                    System.out.println("addFirst: " + v);
                    ref.addFirst(v);
                    myLLDeque.addFirst(v);
                    myADeque.addFirst(v);
                }
                case 2 -> { // test removeLast
                    System.out.println("removeLast: ");
                    Integer refLastItem = ref.pollLast();
                    Integer myLLLastItem = myLLDeque.removeLast();
                    Integer myALastItem = myADeque.removeLast();
                    Assert.assertEquals("RemoveLast wrong in LinkedListDeque", refLastItem, myLLLastItem);
                    Assert.assertEquals("RemoveLast wrong in ArrayDeque", refLastItem, myALastItem);
                }
                case 3 -> { // test removeFirst
                    System.out.println("removeFirst: ");
                    Integer refFirstItem = ref.pollFirst();
                    Integer myLLFirstItem = myLLDeque.removeFirst();
                    Integer myAFirstItem = myADeque.removeFirst();
                    Assert.assertEquals("RemoveFirst wrong in LinkedListDeque", refFirstItem, myLLFirstItem);
                    Assert.assertEquals("RemoveFirst wrong in ArrayDeque", refFirstItem, myAFirstItem);
                }
            }

//            System.out.print("refDeque: ");
//            for (int item : ref) {
//                System.out.print(item + " ");
//            }
//            System.out.println();
//            System.out.print("MyLinkedListDeque: ");
//            myLLDeque.printDeque();
//            System.out.print("MyArrayDeque: ");
//            myADeque.printDeque();

            int expectedSize =  ref.size();
            int actualLLSize = myLLDeque.size();
            int actualASize = myADeque.size();
            Assert.assertEquals("size wrong in LinkedListDeque", expectedSize, actualLLSize);
            Assert.assertEquals("size wrong in ArrayDeque", expectedSize, actualASize);

//            for (int refItem : ref) {
//                Assert.assertEquals(refItem, myLLDeque[i]);
//                Assert.assertEquals(refItem, myADeque[i]);
//            }
        }
    }
}
