import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Shared resource
    private static List<Integer> sharedList = new ArrayList<>();
    // Create a reentrant lock
    private static final ReentrantLock rlock = new ReentrantLock();

    public static void recursiveFunction(int n, int threadId) {
        rlock.lock();
        try {
            System.out.println("Thread " + threadId + ": Acquired the lock at depth " + n + ".");
            if (n > 0) {
                // Recursive call
                recursiveFunction(n - 1, threadId);
            } else {
                // Base case: modify shared resource
                sharedList.add(threadId);
                System.out.println("Thread " + threadId + ": Modified the list to " + sharedList + ".");
            }
            System.out.println("Thread " + threadId + ": Released the lock at depth " + n + ".");
        } finally {
            rlock.unlock();
        }
    }

    public static void main(String[] args) {
        // Create threads
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {  // 3 threads
            final int threadId = i;
            threads[i] = new Thread(() -> recursiveFunction(3, threadId));  // 3 recursive calls
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

        System.out.println("Final shared list: " + sharedList);
    }
}