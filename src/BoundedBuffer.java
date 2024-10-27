import java.util.Arrays;

public class BoundedBuffer<T> {
    private final T[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int size) {
        buffer = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        while (count == buffer.length) {
            System.out.println("Buffer is full");
            wait();
        }
        buffer[in++] = item;
        count++;
        out++;
        System.out.println("Put: " + item);
        System.out.println("Buffer after put: " + '\n' + Arrays.toString(buffer));
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (count == 0) {
            System.out.println("Buffer is empty");
            wait();
        }
        out = count-1;
        T item = buffer[out];
        buffer[out] = null;
        count--;
        in--;
        System.out.println("Take: " + item);
        System.out.println("Buffer after take: " + '\n' + Arrays.toString(buffer));
        notifyAll();
        return item;
    }
}

