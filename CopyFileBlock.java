package lesson6.hw;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyFileBlock {
    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("D:\\Qq.mp4", "r");
            RandomAccessFile rafOut = new RandomAccessFile("D:\\Qq.mp4", "rw");

            ReadWrite[] list = createThreads(raf, rafOut);

            startThreads(list);
            joinThreads(list);
            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ReadWrite[] createThreads(RandomAccessFile rafFile, RandomAccessFile rafCopy) throws IOException {
        long lenFile = rafFile.length();
        int cntTread = Runtime.getRuntime().availableProcessors();
        int bufSize = (lenFile/cntTread) > 8192 ? 8192 : (int)(lenFile/cntTread);
        ReadWrite[] list = new ReadWrite[cntTread];

        for (int i=0; i<cntTread; i++) {
            long start = i*(lenFile/cntTread);
            long end = (i+1 == cntTread) ? lenFile - (lenFile - (((i + 1) * (lenFile/cntTread)) - 1)) + (lenFile%cntTread) : lenFile - (lenFile - (((i + 1) * (lenFile/cntTread)) - 1));
            list[i] = new ReadWrite(rafFile, rafCopy, bufSize, start, end);
        }
        return list;
    }

    public static void startThreads(ReadWrite[] list) {
        for(ReadWrite rw: list) {
            rw.start();
        }
    }

    public static void joinThreads(ReadWrite[] list) {
        for(ReadWrite rw: list) {
            try {
                rw.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                } while (end > start);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}