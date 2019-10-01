package com.java.thread.Interrupt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Task t = new Task();
        Thread thread= new Thread(t);
        thread.start();
       Thread.sleep(900);
        thread.interrupt();

        testInterruptWithLatch();
    }

    public static void testInterruptWithLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TaskWithLatch task= new TaskWithLatch(latch);
        Thread t = new Thread(task);
        t.start();
        t.interrupt();//this is of no use since we are not checking the interrupt flag in thread
        //however if sleep method in TaskWithLatch is uncommented ,then it interrupts sleep which would throw
        // interrupted exception and you can return from there.
        latch.await(3000, TimeUnit.MILLISECONDS);
        System.out.println("Finished awaiting");

    }

}
class TaskWithLatch implements Runnable{
    CountDownLatch latch ;

    public TaskWithLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println("In runUsingThreadPoolExecutor method");
            //Thread.sleep(2000);
            System.out.println("countDowning the latch");
            latch.countDown();
        } catch (Exception e) {
            System.out.println("Interrupted in TaskWithLatch");
            e.printStackTrace();
        }
    }
    /*
    output
    Printing - 0
    Printing - 1
    Printing - 2
    Printing - 3
    Printing - 4
    Printing - 5
    Printing - 6
    Printing - 7
    Printing - 8
    In interrupted Exception
    In runUsingThreadPoolExecutor method
    countDowning the latch
    Finished awaiting
     */
}
