import java.util.concurrent.Semaphore;

public class Main {
    // Semaphore to allow a maximum of 2 threads at a time
    private static final Semaphore semaphore = new Semaphore(2);

    public static void accessResource(int threadId) {
        System.out.println("Thread " + threadId + " is waiting to access the resource...");
        try {
            semaphore.acquire();  // Acquire the semaphore
            System.out.println("Thread " + threadId + " has accessed the resource.");
            Thread.sleep(2000);  // Simulate some work
            System.out.println("Thread " + threadId + " has released the resource.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();  // Release the semaphore
        }
    }

    public static void main(String[] args) {
        // Create threads
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {  // 5 threads
            final int threadId = i;
            threads[i] = new Thread(() -> accessResource(threadId));
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

        System.out.println("All threads have finished.");
    }
}