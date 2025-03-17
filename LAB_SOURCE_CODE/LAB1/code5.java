import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<String> taskA = () -> {
            System.out.println("Task A is starting...");
            TimeUnit.SECONDS.sleep(2);  // Simulate a task taking some time
            System.out.println("Task A is finished after 2 seconds.");
            return "Result from A";
        };

        Callable<String> taskB = () -> {
            System.out.println("Task B is starting...");
            TimeUnit.SECONDS.sleep(3);  // Simulate a task taking some time
            System.out.println("Task B is finished after 3 seconds.");
            return "Result from B";
        };

        Callable<String> taskC = () -> {
            System.out.println("Task C is starting...");
            TimeUnit.SECONDS.sleep(1);  // Simulate a task taking some time
            System.out.println("Task C is finished after 1 second.");
            return "Result from C";
        };

        Callable<String> taskD = () -> {
            System.out.println("Task D is starting...");
            TimeUnit.SECONDS.sleep(4);  // Simulate a task taking some time
            System.out.println("Task D is finished after 4 seconds.");
            return "Result from D";
        };

        try {
            Future<String> futureA = executor.submit(taskA);
            Future<String> futureB = executor.submit(taskB);
            Future<String> futureC = executor.submit(taskC);
            Future<String> futureD = executor.submit(taskD);

            System.out.println(futureA.get());
            System.out.println(futureB.get());
            System.out.println(futureC.get());
            System.out.println(futureD.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}