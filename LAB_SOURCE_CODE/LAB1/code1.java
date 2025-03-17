class MyThreadClass extends Thread {
    public void run() {
        int c = 0;
        while (c < 5) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c++;
            System.out.println("Hello Object");
        }
    }
}

class MyRunnable implements Runnable {
    public void run() {
        int c = 0;
        while (c < 5) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c++;
            System.out.println("Hello Func");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread myThreadFunc = new Thread(new MyRunnable());
        MyThreadClass myThreadObj = new MyThreadClass();
        
        myThreadFunc.start();
        myThreadObj.start();
        
        // Uncomment the following lines if you want the main thread to wait for them
        // try {
        //     myThreadFunc.join();
        //     myThreadObj.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        
        System.out.println("Hello Main");
    }
}
