package com.java.thread.latch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class PerformParallelTaskUsingLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        IntStream.range(1,4)
                .forEach(index -> {
                    Thread t = new ParallelProcessingThread("Thread-"+index,latch);
                    t.start();
                });
        latch.await();
        Thread.sleep(2000);
        System.out.println("All processing completed ! ");
        //+ "All the countdown latch get intimated once the await task is run successfully");
    }
}
/*
Output -
Started thread -Thread-1 to perform parallel task
Started thread -Thread-3 to perform parallel task
Started thread -Thread-2 to perform parallel task
Started thread -Thread-4 to perform parallel task
All processing completed !
Finished performing task for -Thread-3
Finished performing task for -Thread-1
Finished performing task for -Thread-4
Finished performing task for -Thread-2
 */
