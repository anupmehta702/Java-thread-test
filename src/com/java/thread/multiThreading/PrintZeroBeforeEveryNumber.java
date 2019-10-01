package com.java.thread.multiThreading;

/*
Problem statement
 ThreadOne - prints zero before every number
 ThreadTwo - prints odd number
 ThreadThree - prints even number */
public class PrintZeroBeforeEveryNumber {
    public static void main(String[] args) {
        SharedCounterObject sharedObject = new SharedCounterObject();
        Thread threadOne = new Thread(new ThreadOne(sharedObject));
        Thread threadTwo = new Thread(new ThreadTwo(sharedObject));
        Thread threadThree = new Thread(new ThreadThree(sharedObject));
        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }
}

class SharedCounterObject {
    boolean isNumberPrinted = false;
    int counter = 1;
    int limit = 20;
}

class ThreadOne implements Runnable {
    SharedCounterObject sharedObject = new SharedCounterObject();

    public ThreadOne(SharedCounterObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        synchronized (sharedObject) {
            while (sharedObject.counter <= sharedObject.limit) {
                if (sharedObject.isNumberPrinted == true) {
                    System.out.println(" Zero ");
                    sharedObject.isNumberPrinted = false;
                    sharedObject.notifyAll();
                } else {
                    try {
                        sharedObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ThreadTwo implements Runnable {
    SharedCounterObject sharedObject = new SharedCounterObject();

    public ThreadTwo(SharedCounterObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        synchronized (sharedObject) {
            while (sharedObject.counter <= sharedObject.limit) {
                if (sharedObject.isNumberPrinted == false && sharedObject.counter % 2 != 0) {
                    System.out.println("--> " + sharedObject.counter);
                    sharedObject.counter++;
                    sharedObject.isNumberPrinted = true;
                    sharedObject.notifyAll();
                } else {
                    try {
                        sharedObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ThreadThree implements Runnable {
    SharedCounterObject sharedObject = new SharedCounterObject();

    public ThreadThree(SharedCounterObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        synchronized (sharedObject) {
            while (sharedObject.counter <= sharedObject.limit) {
                if (sharedObject.isNumberPrinted == false && sharedObject.counter % 2 == 0) {
                    System.out.println("--> " + sharedObject.counter);
                    sharedObject.counter++;
                    sharedObject.isNumberPrinted = true;
                    sharedObject.notifyAll();
                } else {
                    try {
                        sharedObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
