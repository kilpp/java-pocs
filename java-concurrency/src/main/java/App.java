import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    static int count = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String [] args) {
        singleThreadUsingThread();
    }

    public static void singleThreadUsingThread() {
        System.out.println("SINGLE THREAD USING THREAD");
        RunnablePrint print = new RunnablePrint();
        Thread thread1 = new Thread(print);
        Thread thread2 = new Thread(print);
        Thread thread3 = new Thread(print);
        Thread thread4 = new Thread(print);
        Thread thread5 = new Thread(print);
        Thread thread6 = new Thread(print);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
    }

    public static void singleThread() {
        System.out.println("SINGLE THREAD");
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();
            CallablePrint callable = new CallablePrint();
            Future<String> future1 = executorService.submit(callable);
            Future<String> future2 = executorService.submit(callable);
            Future<String> future3 = executorService.submit(callable);
            System.out.println(future1.get());
            System.out.println(future2.get());
            System.out.println(future3.get());
        } catch (Exception ignored) {
        } finally {
            if(executorService != null) {
                executorService.shutdown();
            }
        }
    }

    public static void multiThread() {
        System.out.println("MULTI THREAD");
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(5);
            executorService.execute(new RunnablePrint());
            CallablePrint callable = new CallablePrint();
            Future<String> future1 = executorService.submit(callable);
            Future<String> future2 = executorService.submit(callable);
            Future<String> future3 = executorService.submit(callable);
            System.out.println(future1.get());
            System.out.println(future2.get());
            System.out.println(future3.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(executorService != null) {
                executorService.shutdown();
            }
        }
    }

    public static void cachedMultiThread() {
        System.out.println("CACHED MULTI THREAD");
        ExecutorService executorService = null;
        try {
            executorService = Executors.newCachedThreadPool();
            executorService.execute(new RunnablePrint());
            Future<String> future1 = executorService.submit(new CallablePrint());
            Future<String> future2 = executorService.submit(new CallablePrint());
            Future<String> future3 = executorService.submit(new CallablePrint());
            System.out.println(future1.get());
            System.out.println(future2.get());
            System.out.println(future3.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(executorService != null) {
                executorService.shutdown();
            }
        }
    }
}
