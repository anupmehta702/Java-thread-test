package com.java.thread.multiThreading;

import org.w3c.dom.css.Counter;

/*
Problem statment -
ThreadA - to print all numbers
ThreadB - to print fizz if number divisible by 3
ThreadC - to print buzz if number divisible by 5
ThreadD - to print fizzBuzz if number divisible by 3,5
 */
public class FizzBuzzProblem {
    public static void main(String[] args) throws InterruptedException {
        CounterObject cObj = new CounterObject();
        Thread threadA= new Thread(new ThreadA(cObj));
        Thread threadB= new Thread(new ThreadB(cObj));
        Thread threadC= new Thread(new ThreadC(cObj));
        Thread threadD= new Thread(new ThreadD(cObj));
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}
class CounterObject{
    int counter=1;
    int limit = 30;
}
class ThreadA implements Runnable{
    CounterObject cObj = new CounterObject();
    public ThreadA(CounterObject cObj) {
        this.cObj = cObj;
    }

    @Override
    public void run() {
        synchronized (cObj){
            while(cObj.counter<=cObj.limit){
                if(cObj.counter%3 != 0 && cObj.counter%5 != 0){
                    System.out.println("-->"+cObj.counter);
                    cObj.counter++;
                    cObj.notifyAll();
                }else{
                    try {
                        cObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
class ThreadB implements Runnable{
    CounterObject cObj = new CounterObject();
    public ThreadB(CounterObject cObj) {
        this.cObj = cObj;
    }

    @Override
    public void run() {
        synchronized (cObj){
            while(cObj.counter<=cObj.limit){
                if(cObj.counter%3 == 0 && cObj.counter%5!=0){
                    System.out.println("-->"+cObj.counter+" Fizz ");
                    cObj.counter++;
                    cObj.notifyAll();
                }else{
                    try {
                        cObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ThreadC implements Runnable{
    CounterObject cObj = new CounterObject();
    public ThreadC(CounterObject cObj) {
        this.cObj = cObj;
    }

    @Override
    public void run() {
        synchronized (cObj){
            while(cObj.counter<=cObj.limit){
                if(cObj.counter%5 == 0 && cObj.counter%3!=0 ){
                    System.out.println("-->"+cObj.counter+" Buzz ");
                    cObj.counter++;
                    cObj.notifyAll();
                }else{
                    try {
                        cObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class ThreadD implements Runnable{
    CounterObject cObj = new CounterObject();
    public ThreadD(CounterObject cObj) {
        this.cObj = cObj;
    }

    @Override
    public void run() {
        synchronized (cObj){
            while(cObj.counter<=cObj.limit){
                if(cObj.counter%5 == 0 && cObj.counter%3 == 0 ){
                    System.out.println("-->"+cObj.counter+" FizzBuzz ");
                    cObj.counter++;
                    cObj.notifyAll();
                }else{
                    try {
                        cObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

