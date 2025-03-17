class MyThreadClassInpOutp extends Thread {
    private int a;
    private int b;

    public MyThreadClassInpOutp(int a) {
        // Constructor logic
        this.a = a;
        this.b = 0;
    }

    @Override
    public void run() {
        int c = 1;
        this.b = 1;
        while (c < (this.a + 1)) {
            this.b = this.b * c;
            c = c + 1;
            System.out.println("Hello Object");
        }
    }

    public int getB() {
        return this.b;
    }
}

public class Main {
    public static void main(String[] args) {
        MyThreadClassInpOutp myThreadObj = new MyThreadClassInpOutp(10);
        System.out.println("value of b before");
        System.out.println(myThreadObj.getB());

        myThreadObj.start();
        try {
            myThreadObj.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("value of b after");
        System.out.println(myThreadObj.getB());
    }
}