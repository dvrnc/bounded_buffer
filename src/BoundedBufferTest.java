import java.util.concurrent.CountDownLatch;

public class BoundedBufferTest {
    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<Integer>(2);
        int countThreads = 100;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(countThreads);
        for (int i = 0; i < countThreads / 2; i++) {
            new Thread(new Put(buffer, startSignal, finishSignal)).start();
            new Thread(new Take(buffer, startSignal, finishSignal)).start();
        }
        System.out.println("Start");
        startSignal.countDown();
        finishSignal.await();
        System.out.println("End");
    }
}
