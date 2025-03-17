import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean dataReady = false;

    public static void producer() {
        lock.lock();
        try {
            System.out.println("Producer is generating data...");
            Thread.sleep(2000);  // Simulate data preparation
            dataReady = true;
            System.out.println("Producer has set dataReady to true and notified consumers.");
            condition.signalAll();  // Notify all waiting threads
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void consumer(int consumerId) {
        lock.lock();
        try {
            System.out.println("Consumer " + consumerId + " is waiting for data...");
            while (!dataReady) {
                condition.await();  // Wait until dataReady is true
            }
            System.out.println("Consumer " + consumerId + " has detected dataReady and proceeds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(Main::producer);
        Thread[] consumerThreads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int consumerId = i;
            consumerThreads[i] = new Thread(() -> consumer(consumerId));
            consumerThreads[i].start();
        }

        producerThread.start();

        try {
            producerThread.join();
            for (Thread t : consumerThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished.");
    }
}