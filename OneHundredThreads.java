package lesson6.hw;

public class OneHundredThreads {
    public static void main(String[] args) {

        for(int i = 0; i<100; i++) {
            MyThread mt = new MyThread();
            mt.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            mt.interrupt();
        }
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