import java.util.concurrent.locks.ReentrantLock;

public class Main {
    // Shared resource
    private static int counter = 0;
    // Create a lock
    private static final ReentrantLock lock = new ReentrantLock();

    // Version 1
    public static void incrementCounter(int threadId) {
        for (int i = 0; i < 5; i++) {
            lock.lock();  // Acquire the lock
            try {
                int temp = counter;
                System.out.println("Thread " + threadId + ": Read counter as " + temp);
                Thread.sleep(100);  // Simulate processing
                counter = temp + 1;
                System.out.println("Thread " + threadId + ": Updated counter to " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();  // Release the lock
            }
        }
    }

    // Version 2
    public static void incrementCounterWithTryLock(int threadId) {
        for (int i = 0; i < 5; i++) {
            if (lock.tryLock()) {  // Acquire the lock
                try {
                    int temp = counter;
                    System.out.println("Thread " + threadId + ": Read counter as " + temp);
                    Thread.sleep(100);  // Simulate processing
                    counter = temp + 1;
                    System.out.println("Thread " + threadId + ": Updated counter to " + counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();  // Release the lock
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create threads
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {  // 3 threads
            final int threadId = i;
            threads[i] = new Thread(() -> incrementCounter(threadId));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final counter value: " + counter);
    }
}