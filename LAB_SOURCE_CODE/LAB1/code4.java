import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Callable<Long> task = () -> {
            return (long) Math.pow(323, 1235);
        };

        Future<Long> future = executor.submit(task);

        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}