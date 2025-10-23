import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CallablePrint implements Callable<String> {
    private final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public String call() {
        boolean isLocked = lock.tryLock();
        if(isLocked) {
            App.count++;
            return "EXECUTED - COUNT " + App.count + " ATOMIC " + App.atomicCount.incrementAndGet();
        }
        lock.unlock();
        return "UNABLE TO EXECUTE - COUNT " + App.count + " ATOMIC " + App.atomicCount.incrementAndGet();
    }
}
