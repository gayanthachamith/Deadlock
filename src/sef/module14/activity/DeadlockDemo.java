package sef.module14.activity;

public class DeadlockDemo {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread A: locked lock1");
                sleep(200);
                System.out.println("Thread A: trying to lock lock2...");
                synchronized (lock2) {
                    System.out.println("Thread A: locked lock2");
                }
            }
        }, "Thread-A");

        Thread threadB = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread B: locked lock2");
                sleep(200);
                System.out.println("Thread B: trying to lock lock1...");
                synchronized (lock1) {
                    System.out.println("Thread B: locked lock1");
                }
            }
        }, "Thread-B");

        threadA.start();
        threadB.start();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
