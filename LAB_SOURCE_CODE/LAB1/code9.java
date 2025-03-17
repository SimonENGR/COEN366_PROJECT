import java.util.concurrent.CountDownLatch;

public class Main {
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void worker() {
        try {
            System.out.println("Worker is waiting for the event to be set.\n");
            latch.await();  // Block until the event is set
            System.out.println("Worker has detected the event is set and proceeds.\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void controller() {
        try {
            System.out.println("Controller is sleeping for 3 seconds before setting the event.\n");
            Thread.sleep(3000);
            latch.countDown();  // Set the event
            System.out.println("Controller has set the event.\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(Main::worker);
        Thread thread2 = new Thread(Main::controller);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}