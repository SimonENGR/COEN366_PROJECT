import java.util.concurrent.CyclicBarrier;

public class Main {
    private static final CyclicBarrier barrier = new CyclicBarrier(3);

    public static void worker(int threadId) {
        try {
            System.out.println("Thread " + threadId + " is working and waiting at the barrier.");
            Thread.sleep(threadId * 1000);  // Simulate varying work times
            barrier.await();  // Block until all threads reach the barrier
            System.out.println("Thread " + threadId + " has crossed the barrier.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create threads
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {  // 3 threads, matching the barrier count
            final int threadId = i;
            threads[i] = new Thread(() -> worker(threadId));
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

        System.out.println("All threads have crossed the barrier.");
    }
}