package com.java.thread.Interrupt;

public class Task implements Runnable {
    @Override
    public void run() {
        int i = 0;
        while(i<1000) {
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println("Printing - " + i);
                i++;
            } else {
                System.out.println("It is interrupted");//this never gets printed
            }
        }
    }
}
