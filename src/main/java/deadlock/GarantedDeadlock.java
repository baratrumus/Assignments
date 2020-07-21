package deadlock;
import java.util.concurrent.CountDownLatch;
/**
 * Нужно написать программу, которая всегда падает в дедлок.
  в программе нельзя использовать метод sleep.
 */
public class GarantedDeadlock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        CountDownLatch latch = new CountDownLatch(2);
        Thread1 t1 = new Thread1(lock1, lock2, latch);
        Thread2 t2 = new Thread2(lock1, lock2, latch);
        t1.start();
        t2.start();
    }
}

class Thread1 extends Thread {
    private CountDownLatch latch;
    private final Object lock1;
    private final Object lock2;

    Thread1(Object lock1, Object lock2, CountDownLatch latch) {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            latch.countDown();
            System.out.println("Thread 1: Holding lock 1...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1: Waiting for lock 2...");
            synchronized (lock2) {
                System.out.println("Thread 1: Holding lock 1 & 2...");
            }
        }
    }
}

class Thread2 extends Thread {
    private final CountDownLatch latch;
    private final Object lock1;
    private final Object lock2;

    Thread2(Object lock1, Object lock2, CountDownLatch latch) {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.latch = latch;
    }
    @Override
    public void run() {
        synchronized (lock2) {
            latch.countDown();
            System.out.println("Thread 2: Holding lock 2...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2: Waiting for lock 1...");
            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock 1 & 2...");
            }
        }
    }
}

