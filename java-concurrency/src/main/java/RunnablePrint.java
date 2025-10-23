import java.util.concurrent.locks.ReentrantLock;

public class RunnablePrint implements Runnable {
    private final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        boolean isLocked = lock.tryLock();
        if (isLocked) {
            App.count++;
            System.out.println("EXECUTED - COUNT " + App.count + " ATOMIC " + App.atomicCount.incrementAndGet());
            lock.unlock();
        }
    }
}
