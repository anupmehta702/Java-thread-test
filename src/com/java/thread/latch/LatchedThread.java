package com.java.thread.latch;

import java.util.concurrent.CountDownLatch;

public class LatchedThread extends Thread {
    String threadName;
    private final CountDownLatch latch ;

    public LatchedThread(int threadCount,CountDownLatch latch) {
        this.threadName = "Thread -"+threadCount;
        this.latch=latch;
    }

    public void run(){
        System.out.println("Starting thread -"+threadName);
        try {
            latch.await();
            System.out.println("Running logic for thread -"+threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
