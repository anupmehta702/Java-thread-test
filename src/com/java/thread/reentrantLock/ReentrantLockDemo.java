package com.java.thread.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable {
    private final Lock lock;

    public ReentrantLockDemo(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock.tryLock()) {
            lock.lock();
            try {
                System.out.println("Printing by thread --> " + Thread.currentThread().getName());
            }  finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread one = new Thread(new ReentrantLockDemo(lock));
        Thread two = new Thread(new ReentrantLockDemo(lock));
        one.start();
        two.start();
    }
}
/*Output
With tryLock method -
Printing by thread --> Thread-0
Printing by thread --> Thread-1
with tryLock method -
Printing by thread --> Thread-0
 */
