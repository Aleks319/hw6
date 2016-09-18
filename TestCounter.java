package lesson6.hw;

public class TestCounter {
    public static void main(String[] args) throws InterruptedException {
        CounterNew c = new CounterNew(125, 456);
        c.start();
        c.join();
        System.out.println("Done!");
    }
}

class CounterNew  extends Thread {
    private int rngStart;
    private int rngEnd;

    public CounterNew(int rngStart, int rngEnd) {
        this.rngStart = rngStart;
        this.rngEnd = rngEnd;
    }

    @Override
    public void run() {
        if(rngStart > rngEnd) {
            System.out.println("rngStart must be less or equal than rngEnd");
            return;
        }
        int x = rngStart;
        while (x <= rngEnd)
            System.out.println(x++);
    }
}