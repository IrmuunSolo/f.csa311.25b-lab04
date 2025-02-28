package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
    //      mQueue = new LinkedIntQueue();
          mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(1);
        assertEquals(Integer.valueOf(1), mQueue.peek());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (int num : testList) {
            mQueue.enqueue(num);
        }
        
        for (int num : testList) {
            assertEquals(Integer.valueOf(num), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testDequeueEmptyQueue() {
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testEnqueueDequeueMultiple() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        mQueue.enqueue(3);
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testArrayIntQueueBug() {
        // ArrayIntQueue болгон сольж бүтцийн тест хийх
        mQueue = new ArrayIntQueue();
        mQueue.enqueue(10);
        mQueue.enqueue(20);
        mQueue.enqueue(30);
        
        assertEquals(Integer.valueOf(10), mQueue.dequeue());
        assertEquals(Integer.valueOf(20), mQueue.peek());
        assertEquals(Integer.valueOf(20), mQueue.dequeue());
        assertEquals(Integer.valueOf(30), mQueue.dequeue());
        assertNull(mQueue.dequeue()); // хоосон үед null буцаана
    }

    @Test
    public void testEnsureCapacity() {
        mQueue = new ArrayIntQueue();
        // Дарааллыг анхны багтаамжаар нь дүүргэх
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        
        // ensureCapacity ажиллуулахын тулд илүү элемент нэмэх
        mQueue.enqueue(10);
        
        // дараалал нь тэлж бүх элементийг агуулж байгааг бататгах
        assertEquals(11, mQueue.size());
        for (int i = 0; i <= 10; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testClear() {
        mQueue = new ArrayIntQueue(); // зөвхөн ArrayIntQueue классд ажилхаар тохируулсан байна
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        assertFalse(mQueue.isEmpty());
        
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }


}
