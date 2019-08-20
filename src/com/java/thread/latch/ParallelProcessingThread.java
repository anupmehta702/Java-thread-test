package com.java.thread.latch;

import java.util.concurrent.CountDownLatch;

public class ParallelProcessingThread extends Thread {
    String threadName;
    CountDownLatch latch;

    public ParallelProcessingThread(String threadName, CountDownLatch latch) {
        this.threadName = threadName;
        this.latch = latch;
    }
    public void run(){
        System.out.println("Started thread -"+threadName+" to perform parallel task");
        try {
            latch.countDown();
            if(threadName.equalsIgnoreCase("Thread-2")){
                Thread.sleep(6000);
            }
            System.out.println("Finished performing task for -"+threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
