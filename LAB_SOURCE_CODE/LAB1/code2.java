import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// Example 2: Thread class and function
class MyThreadClass extends Thread {
    private int a;

    public MyThreadClass(int a) {
        this.a = a;
    }

    public void run() {
        int c = 0;
        while (c < a) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            c++;
            System.out.println("Hello Object");
        }
    }
}

class ThreadFunction implements Runnable {
    private int a, b;

    public ThreadFunction(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public void run() {
        int c = 0;
        while (c < a) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            c++;
            System.out.println("Hello Func " + b);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread funcThread = new Thread(new ThreadFunction(5, 6));
        MyThreadClass objThread = new MyThreadClass(5);

        funcThread.start();
        objThread.start();

        try {
            funcThread.join();
            objThread.join();
        } catch (InterruptedException e) {}

        System.out.println("Hello Main");
    }
}
