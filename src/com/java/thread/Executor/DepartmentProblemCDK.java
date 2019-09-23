package com.java.thread.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/*
Problem statement  - We have to process events of multiple departments in parallel whereas within the department it should be in sequence.
For example - Mechnical ,Electrical ,Computers have several events ,all can be processed parrallelly but the events for Mechincal
need to be processed in serial.
*/

class Event implements Comparable<Event> {
    String deptName = "";
    String paper = "";

    public Event(String deptName, String paper) {
        this.deptName = deptName;
        this.paper = paper;
    }

    @Override
    public int compareTo(Event o) {
        return this.paper.compareTo(o.paper);
    }

    @Override
    public String toString() {
        return "Event{" + "deptName='" + deptName + '\'' + ", paper='" + paper + '\'' + '}';
    }
}

class Department {

    String departmentName;
    List<String> paperList = new ArrayList<>();

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public void print() {
        System.out.println("Department --> " + departmentName + " Paper list --> " + paperList);
    }

    public synchronized void addPaper(String paper) throws InterruptedException {
        if (departmentName == "M") {
            Thread.sleep(4000);
        }
        paperList.add(paper);
    }

}

class DepartmentThread implements Runnable {

    String departmentName;
    Queue<Event> departmentQueue = new PriorityQueue<>();//It uses Heap internally,bcoz of which it is not FIFO .
    // Everytime event with max priority gets extracted
    List<Event> departmentEventList = new ArrayList<>();

    public DepartmentThread(String departmentName) {
        this.departmentName = departmentName;
    }

    public void print() {
        System.out.println("Department --> " + departmentName + " Event queue --> "+departmentEventList);
    }

    public void addEvents(Event event) {
        departmentQueue.add(event);
        departmentEventList.add(event);
    }

    @Override
    public synchronized void run() {
        while (departmentEventList.size() != 0) {
            Event event = departmentEventList.remove(0);
            try {
                if (event.deptName == "M") {
                    Thread.sleep(4000);
                } else if (event.deptName == "C") {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Processing event with department - " + event.deptName + " paper - " + event.paper);
        }
        System.out.println("Finished processing all events for department -" + departmentName);
    }
}

public class DepartmentProblemCDK {

    public static void main(String[] args) throws InterruptedException {
        //runOnlyExecutor();
        //runUsingThreadPoolExecutor();
        runForParallelAndSequentialProcessing();
    }

    public static void populateEvents(Queue<Event> bq) {
        bq.add(new Event("M", "P1"));
        bq.add(new Event("M", "P2"));
        bq.add(new Event("M", "P3"));
        bq.add(new Event("E", "P3"));
        bq.add(new Event("C", "P1"));
        bq.add(new Event("E", "P2"));
        bq.add(new Event("C", "P2"));
        bq.add(new Event("E", "P1"));

        /* Internally LinkedBlockingQueue calls below method
         Node<E> node = new Node<E>(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return linkLast(node);
        } finally {
            lock.unlock();
        }

        For ArrayBlockingQueue ,it calls below method
         public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length)
                return false;
            else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

         */

    }

    public static void runOnlyExecutor() {
        Executor executor = Executors.newFixedThreadPool(5);
        IntStream.range(1, 10)
                .forEach((i) -> {
                    executor.execute(() -> {
                        try {
                            System.out.println("Running executor by Thread " + Thread.currentThread().getName());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
        ((ExecutorService) executor).shutdown();
        /*OUTPUT
        Processing department M for paper P2 by thread pool-1-thread-2
        Processing department M for paper P1 by thread pool-1-thread-1
        Processing department M for paper P3 by thread pool-1-thread-2
        Processing department E for paper P3 by thread pool-1-thread-1
        Processing department C for paper P1 by thread pool-1-thread-1
        Processing department E for paper P2 by thread pool-1-thread-1
        Processing department C for paper P2 by thread pool-1-thread-1
        Processing department E for paper P1 by thread pool-1-thread-1
         */

    }

    public static void runUsingThreadPoolExecutor() throws InterruptedException {
        //Problem with this approach is if no of threads are reduced to 2 ,first two threads get blocked until M is processed .
        //Assuming M takes lot of time to process .
        BlockingQueue<Event> bq = new LinkedBlockingQueue<>(10);
        Department m = new Department("M");
        Department e = new Department("E");
        Department c = new Department("C");
        Executor executor = Executors.newFixedThreadPool(2);
        populateEvents(bq);
        while (bq.peek() != null) {
            Event event = bq.take();
            if (event != null) {
                executor.execute(() -> {
                    try {
                        System.out.println("Processing department " + event.deptName + " for paper " + event.paper + " by thread " + Thread.currentThread().getName());
                        if (event.deptName.equalsIgnoreCase("M")) {
                            m.addPaper(event.paper);
                        } else if (event.deptName.equalsIgnoreCase("E")) {
                            e.addPaper(event.paper);
                        } else {
                            c.addPaper(event.paper);
                        }
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
            }

        }

        ((ExecutorService) executor).shutdown();
        ((ExecutorService) executor).awaitTermination(8000, TimeUnit.MILLISECONDS);
        /*m.print();
        e.print();
        c.print();*/
    }

    public static void runForParallelAndSequentialProcessing() throws InterruptedException {
        BlockingQueue<Event> bq = new LinkedBlockingQueue<>(10);
        DepartmentThread m = new DepartmentThread("M");
        DepartmentThread e = new DepartmentThread("E");
        DepartmentThread c = new DepartmentThread("C");
        Executor executor = Executors.newFixedThreadPool(1);//deliberately set to 1 so that events get put into resp department list
        //in order in which we receive
        populateEvents(bq);
        while (bq.peek() != null) {
            Event event = bq.take();
            if (event != null) {
                executor.execute(() -> {
                    System.out.println("Adding event with department " + event.deptName + " for paper " + event.paper + " by thread " + Thread.currentThread().getName());
                    if (event.deptName.equalsIgnoreCase("M")) {
                        m.addEvents(event);
                    } else if (event.deptName.equalsIgnoreCase("E")) {
                        e.addEvents(event);
                    } else {
                        c.addEvents(event);
                    }
                });
            }

        }

        ((ExecutorService) executor).shutdown();
        ((ExecutorService) executor).awaitTermination(8000, TimeUnit.MILLISECONDS);
        m.print();
        e.print();
        c.print();

        Thread mt = new Thread(m);
        mt.start();
        Thread ct = new Thread(c);
        ct.start();
        Thread et = new Thread(e);
        et.start();
        /*Output
    Adding event with department M for paper P1 by thread pool-1-thread-1
    Adding event with department M for paper P2 by thread pool-1-thread-1
    Adding event with department M for paper P3 by thread pool-1-thread-1
    Adding event with department E for paper P3 by thread pool-1-thread-1
    Adding event with department C for paper P1 by thread pool-1-thread-1
    Adding event with department E for paper P2 by thread pool-1-thread-1
    Adding event with department C for paper P2 by thread pool-1-thread-1
    Adding event with department E for paper P1 by thread pool-1-thread-1
    Department --> M Event queue --> [Event{deptName='M', paper='P1'}, Event{deptName='M', paper='P2'}, Event{deptName='M', paper='P3'}]
    Department --> E Event queue --> [Event{deptName='E', paper='P3'}, Event{deptName='E', paper='P2'}, Event{deptName='E', paper='P1'}]
    Department --> C Event queue --> [Event{deptName='C', paper='P1'}, Event{deptName='C', paper='P2'}]
    Processing event with department - E paper - P3
    Processing event with department - E paper - P2
    Processing event with department - E paper - P1
    Finished processing all events for department -E
    Processing event with department - C paper - P1
    Processing event with department - C paper - P2
    Finished processing all events for department -C
    Processing event with department - M paper - P1
    Processing event with department - M paper - P2
    Processing event with department - M paper - P3
    Finished processing all events for department -M
 */

    }


}


