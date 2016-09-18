package lesson6.hw;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyFileWithProgress {
    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("D:\\Qq.mp4", "r");
            RandomAccessFile rafOut = new RandomAccessFile("D:\\Qq.mp4", "rw");

            ReadWrite rw = new ReadWrite(raf, rafOut, 8192, 0, raf.length());
            long s = 0;
            try {
                s = System.currentTimeMillis();
                rw.start();
                rw.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("100% copied!");
            long e = System.currentTimeMillis();
            System.out.println(e-s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class ReadWrite extends Thread {
        private RandomAccessFile rafFile;
        private RandomAccessFile rafCopy;
        private byte buf[];
        private long start;
        private long end;

        public ReadWrite(RandomAccessFile rafFile, RandomAccessFile rafCopy, int buf, long start, long end) {
            this.rafFile = rafFile;
            this.rafCopy = rafCopy;
            this.buf = new byte[buf];
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            if(end < start || start < 0 || end < 0) {
                return;
            }
            try {
                do {
                    rafFile.seek(start);
                    int r = rafFile.read(buf);
                    if ((long) (start + r) > end) {
                        r = (int) (end - start);
                        if (r < 0) {
                            return;
                        }
                    }
                    rafCopy.seek(start);
                    rafCopy.write(buf, 0, r);
                    start += buf.length;
                    System.out.println((int)(start*100/end) + "% copied...");
                } while (end > start);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}