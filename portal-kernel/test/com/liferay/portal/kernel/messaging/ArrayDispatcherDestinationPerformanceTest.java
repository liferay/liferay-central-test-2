/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liferay.portal.kernel.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author zsyxc
 */
public class ArrayDispatcherDestinationPerformanceTest {

    public static int LISTENER_NUMBER = 10000;
    public static int REGISTER_THREAD_NUMBER = 10;
    public static int UNREGISTER_THREAD_NUMBER = 10;
    public static int ITERATE_TIMES = 100;
    public static int TEST_THREADPOOL_SIZE = REGISTER_THREAD_NUMBER + UNREGISTER_THREAD_NUMBER;
    private ExecutorService executorService;
    private Destination testDestination;
    private MessageListener[] testListeners;
    private List<Callable<Object>> tasks;
    private long startTime;

    @Before
    public void setUp() {
        printDescription();
        executorService = Executors.newFixedThreadPool(TEST_THREADPOOL_SIZE);
        testDestination = new TestArrayDispatcherDestination();
        testListeners = new MessageListener[LISTENER_NUMBER];
        for (int i = 0; i < LISTENER_NUMBER; i++) {
            testListeners[i] = new FakeMessageListener();
        }
        tasks = new ArrayList<Callable<Object>>(TEST_THREADPOOL_SIZE);
        for (int i = 0; i < REGISTER_THREAD_NUMBER; i++) {
            tasks.add(new RegisterTask(testDestination));
        }
        for (int i = 0; i < UNREGISTER_THREAD_NUMBER; i++) {
            tasks.add(new UnregisterTask(testDestination));
        }
        startTime = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws InterruptedException {
        executorService.shutdownNow();
        executorService.awaitTermination(120, TimeUnit.SECONDS);
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time is " + totalTime + " ms");
    }

    @Test
    public void testPerformance() throws InterruptedException {
        executorService.invokeAll(tasks);
    }

    private void printDescription() {
        System.out.println("Performance test for ArrayDispatcherDestination.\n");
        System.out.println("Candidate MessageListener number:\t\t" + LISTENER_NUMBER);
        System.out.println("Register thread nunmber:\t\t\t" + REGISTER_THREAD_NUMBER);
        System.out.println("Unregister thread number:\t\t\t" + UNREGISTER_THREAD_NUMBER);
        System.out.println("Iterate times:\t\t\t\t" + ITERATE_TIMES);
        System.out.println("Test thread pool size:\t\t\t" + TEST_THREADPOOL_SIZE);
    }

    private class RegisterTask implements Callable<Object> {

        private final Destination destination;

        public RegisterTask(Destination destination) {
            this.destination = destination;
        }

        public Object call() {
            Random random = new Random();
            for (int i = 0; i < ITERATE_TIMES; i++) {
                MessageListener listener = testListeners[random.nextInt(LISTENER_NUMBER)];
                destination.register(listener);
            }
            return null;
        }
    }

    private class UnregisterTask implements Callable<Object> {

        private final Destination destination;

        public UnregisterTask(Destination destination) {
            this.destination = destination;
        }

        public Object call() {
            Random random = new Random();
            for (int i = 0; i < ITERATE_TIMES; i++) {
                MessageListener listener = testListeners[random.nextInt(LISTENER_NUMBER)];
                destination.unregister(listener);
            }
            return null;
        }
    }
}

/**
 * A test implemention for ArrayDispatcherDestination. The dispatch method will
 * take constant time(from constructor parameter).
 * @author zsyxc
 */
class TestArrayDispatcherDestination extends ArrayDispatcherDestination {

    public TestArrayDispatcherDestination() {
        super("TestArrayDispatcherDestination");
    }

    protected void dispatch(MessageListener[] listeners, Message message) {
        //Do nothing
    }
}

class FakeMessageListener implements MessageListener {

    public void receive(Message message) {
        //Do nothing
    }
}
