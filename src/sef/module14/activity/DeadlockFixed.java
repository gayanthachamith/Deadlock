package sef.module14.activity;

public class DeadlockFixed {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> doWork(lock1, lock2), "Thread-A");
        Thread threadB = new Thread(() -> doWork(lock2, lock1), "Thread-B"); // even if passed reversed, ordering fixes it

        threadA.start();
        threadB.start();
    }

    private static void doWork(Object a, Object b) {
        // Global lock ordering rule: always lock the "smaller" identity hash first
        Object first = a;
        Object second = b;

        if (System.identityHashCode(first) > System.identityHashCode(second)) {
            first = b;
            second = a;
        }

        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + ": locked FIRST");
            sleep(200);
            System.out.println(Thread.currentThread().getName() + ": trying to lock SECOND...");
            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + ": locked SECOND (no deadlock)");
            }
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
