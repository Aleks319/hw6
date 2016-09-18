package lesson6.hw;

public class FiftyThreads {
    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<50; i++) {
                    MyThread mt = new MyThread();
                    mt.start();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    mt.interrupt();
                }
            }
        }
        );

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
        }
        System.out.println("Done!");
    }

    public static class MyThread extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()){
                try {
                    System.out.println(this.getName());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}