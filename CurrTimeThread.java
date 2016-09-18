package lesson6.hw;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CurrTimeThread {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        CurrTime ct = new CurrTime();
        ct.start();
        String stop;
        while (true) {
            stop = sc.nextLine();
            if(stop.equals("stop")) {
                ct.interrupt();
                break;
            }
        }
    }

    public static class CurrTime extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    return;
                }
                System.out.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS").format(System.currentTimeMillis()));
            }
        }
    }
}