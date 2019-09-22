package com.java.thread.multiThreading;

public class Thread1 implements Runnable {
    SharedObject sharedObject;
    String threadName =" Thread -1 ";
    Integer sharedIntegerObject = new Integer(0);
    public Thread1(SharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }
    public Thread1(Integer sharedIntegerObject) {
        this.sharedIntegerObject = sharedIntegerObject;
    }

    @Override
    public void run() {
        while(sharedObject.isCountLessThanMaxCount()){
            synchronized (sharedObject){
                if(sharedObject.count % 2 == 0 ){
                    System.out.println(threadName+" -- "+ sharedObject.count);
                    sharedObject.count++;
                    sharedObject.notify();//By mistake I did this.notify .
                    // notify and wait should always be called on locked object
                }else{
                    try {
                        sharedObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

  /*  public void runUsingThreadPoolExecutor() {
        while(sharedIntegerObject <= 10){
            synchronized (sharedIntegerObject){
                if(sharedIntegerObject % 2 == 0 ){
                    System.out.println(threadName+" -- "+ sharedIntegerObject);
                    sharedIntegerObject=sharedIntegerObject+1;
                    sharedIntegerObject.notify();//By mistake I did this.notify .
                    // notify and wait should always be called on locked object
                }else{
                    try {
                        sharedIntegerObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }*/
}
