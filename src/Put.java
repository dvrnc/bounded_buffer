import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Put implements Runnable {
    private final BoundedBuffer<Integer> buffer;
    private final CountDownLatch startSignal;
    private final CountDownLatch finishSignal;

    public Put(BoundedBuffer<Integer> buffer, CountDownLatch startSignal, CountDownLatch finishSignal) {
        this.buffer = buffer;
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            startSignal.await();
            buffer.put(random.nextInt(0, 9));
            finishSignal.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
