package com.java.thread.multiThreading;

/*
Thread 3 prints 1
Thread 4 prints 2 and so on
 */
public class PrintNumbersUsingThreads {
    static boolean x=false;
    PrintNumbersUsingThreads() throws Exception{
        x=true;
        if(1==1) {
            throw new Exception();
        }

    }
    public static void main(String[] args) throws Exception {
        PrintNumbersUsingThreads p = new PrintNumbersUsingThreads();
        System.out.println(p);
        /*
        Cant share Integer as a shared object bcoz if you change the value of integer it creates a new object
        Integer sharedIntegerObject = new Integer(1);
        Thread t1 = new Thread(new Thread1(sharedIntegerObject));
        Thread t2 = new Thread(new Thread2(sharedIntegerObject));
        t2.start();
        t1.start();*/

        SharedObject sharedObject = new SharedObject(1, 5);
        Thread t3 = new Thread(() -> {
            while (sharedObject.isCountLessThanMaxCount()) {
                synchronized (sharedObject) {
                    if (sharedObject.count % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " -- " + sharedObject.count);
                        sharedObject.count++;
                        sharedObject.notify();//By mistake I did this.notify .
                        // notify and wait should always be called on locked object
                    } else {
                        try {
                            sharedObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread t4 = new Thread(() -> {
            while (sharedObject.isCountLessThanMaxCount()) {
                synchronized (sharedObject) {//cannot synchronize on Integer object
                    if (sharedObject.count % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + " -- " + sharedObject.count);
                        sharedObject.count++;
                        sharedObject.notify();//By mistake I did this.notify .
                        // notify and wait should always be called on locked object
                    } else {
                        try {
                            sharedObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t3.start();
        t4.start();
    }
}
/*
Output --
Thread-1 -- 1
Thread-0 -- 2
Thread-1 -- 3
Thread-0 -- 4
Thread-1 -- 5
 */

