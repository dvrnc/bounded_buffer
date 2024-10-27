import java.util.concurrent.CountDownLatch;

public class Take implements Runnable {
    private final BoundedBuffer<Integer> buffer;
    private CountDownLatch startSignal;
    private CountDownLatch finishSignal;

    public Take(BoundedBuffer<Integer> buffer, CountDownLatch startSignal, CountDownLatch finishSignal) {
        this.buffer = buffer;
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            buffer.take();
            finishSignal.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
