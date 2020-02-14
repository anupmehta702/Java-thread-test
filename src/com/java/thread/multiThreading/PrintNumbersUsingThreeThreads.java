package com.java.thread.multiThreading;

import static com.java.thread.multiThreading.PrintNumbersUsingThreeThreads.*;

/*
Thread-1 -->1
Thread-2-->2
Thread-3-->3
Thread-1-->4
Thread-2-->5
Thread-3-->6

 */
public class PrintNumbersUsingThreeThreads {
    public static int count =1;

    public static void main(String[] args) {
        Object sharedObject = new Object();//used a Object as sharedObject
        Thread t1 = new Thread(new PrintNumber(sharedObject,1,"Thread-1"));
        Thread t2 = new Thread(new PrintNumber(sharedObject,2,"Thread-2"));
        Thread t3 = new Thread(new PrintNumber(sharedObject,0,"Thread-3"));
        t1.start();
        t2.start();
        t3.start();

    }
}
class PrintNumber implements Runnable{
    Object sharedObject;
    int remainder ;
    String threadName;

    public PrintNumber(Object sharedObject, int remainder, String threadName) {
        this.sharedObject = sharedObject;
        this.remainder = remainder;
        this.threadName=threadName;
    }

    @Override
    public void run() {
        while (count <= 10) {
            synchronized (sharedObject) {
                if (count % 3 == remainder) {
                    System.out.println("" + threadName + "-->" + count);
                    count++;
                    sharedObject.notifyAll();
                } else {
                    try {
                        sharedObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }//synchronized loop
        }//while loop
    }
}

