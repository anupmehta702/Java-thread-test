package com.java.thread.reentrantLock;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition {
    Stack<String> stack = new Stack();
    int capacity = 5;
    ReentrantLock lock = new ReentrantLock();
    Condition stackFullCondition = lock.newCondition();
    Condition stackEmptyCondition = lock.newCondition();

    public void push(String input) throws InterruptedException {
        lock.lock();
        try {
            while (stack.size() == capacity) {
                stackFullCondition.await();
            }
            stack.push(input);
            System.out.println(input+" pushed to stack ");
        } finally {
            stackEmptyCondition.signalAll();//signalling EMPTY condition and not FULL
            lock.unlock();
        }
    }

    public String pop() throws InterruptedException {
        lock.lock();
        try {
            while (stack.size() == 0) {
                stackEmptyCondition.await();
            }
            return stack.pop();
        } finally {
            stackFullCondition.signalAll();//signalling FULL condition and not EMPTY
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition rlc = new ReentrantLockCondition();
        Thread t1 = new Thread(new Thread1(rlc));
        Thread t2 = new Thread(new Thread2(rlc));
        t1.start();
        t2.start();
    }
}
class Thread1 implements Runnable{
    ReentrantLockCondition rlc ;

    public Thread1(ReentrantLockCondition rlc) {
        this.rlc = rlc;
    }

    @Override
    public void run() {
        try {
            rlc.push("one");
            rlc.push("two");
            rlc.push("three");
            rlc.push("four");
            rlc.push("five");
            rlc.push("six");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class Thread2 implements Runnable{
    ReentrantLockCondition rlc ;

    public Thread2(ReentrantLockCondition rlc) {
        this.rlc = rlc;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Popping-->" + rlc.pop());
            Thread.sleep(1000);
            System.out.println("Popping-->" + rlc.pop());
            Thread.sleep(1000);
            System.out.println("Popping-->" + rlc.pop());
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
/*output
one pushed to stack
two pushed to stack
three pushed to stack
four pushed to stack
five pushed to stack
Popping-->five
six pushed to stack
Popping-->six
Popping-->four
 */
