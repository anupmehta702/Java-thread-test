package com.java.thread.Interrupt;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Task t = new Task();
        Thread thread= new Thread(t);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
