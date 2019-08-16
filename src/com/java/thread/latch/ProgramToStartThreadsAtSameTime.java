package com.java.thread.latch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class ProgramToStartThreadsAtSameTime {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        IntStream.range(1,4).
                forEach((i)->{
                    Thread t= new LatchedThread(i,latch);
                    t.start();
                });
        Thread.sleep(2000);
        latch.countDown();
        System.out.println("Finished countDown latch");

    }
}
/*
Output -
Without latch
Starting thread -Thread -1
Running logic for thread -Thread -1
Starting thread -Thread -3
Running logic for thread -Thread -3
Starting thread -Thread -2
Running logic for thread -Thread -2
With latch -
Starting thread -Thread -1
Starting thread -Thread -3
Starting thread -Thread -2
Running logic for thread -Thread -1
Running logic for thread -Thread -3
Running logic for thread -Thread -2
 */
