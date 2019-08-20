package com.java.thread.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class TestCyclicBarrier {

    public static void main(String[] args) throws InterruptedException {
        checkReusability();
        testCyclicBarrier();
    }
    public static void testCyclicBarrier(){
        List<Integer> input = new ArrayList<>();
        input.add(1);
        CyclicBarrier barrier = new CyclicBarrier(3,new AggregatorThread(input));
        System.out.println("Count of waiting threads on barrier before --"+barrier.getNumberWaiting());
        IntStream.range(1,4)
                .forEach((index)->{
                    Thread worker = new Thread( new WorkerThread(barrier,index,input));
                    worker.start();
                });

    }
    public static void checkReusability() throws InterruptedException {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        CyclicBarrier barrier = new CyclicBarrier(3,new AggregatorThread(input));
        System.out.println("Count of waiting threads on barrier before --"+barrier.getNumberWaiting());
        IntStream.range(1,4)
                .forEach((index)->{
                    Thread worker = new Thread( new WorkerThread(barrier,index,input));
                    worker.start();
                });
        Thread.sleep(4000);
        System.out.println("Starting again the worker threads to see if cyclic barrier count gets reset");
        IntStream.range(4,7)
                .forEach((index)->{
                    Thread worker = new Thread( new WorkerThread(barrier,index,input));
                    worker.start();
                });
    }
}

/*
Starting --Thread-1
Starting --Thread-3
Starting --Thread-2
Added items --[5, 2, 2] by --Thread-3
Added items --[7, 0, 5] by --Thread-2
Added items --[9, 3, 8] by --Thread-1
Starting -- AggregatorThread Waiting for 4 secs to start working
Final result -- 42
Finished work by -- AggregatorThread
Finished work by -- Thread-1 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-3 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-2 This is finished only after aggregator thread is done with it's work
 */

/*
Reusability test output
Count of waiting threads on barrier before --0
Starting --Thread-1
Starting --Thread-2
Starting --Thread-3
Added items --[3, 7, 3] by --Thread-3
Added items --[6, 1, 8] by --Thread-2
Added items --[6, 6, 1] by --Thread-1
Starting -- AggregatorThread Waiting for 4 secs to start working
Final result -- 42
Finished work by -- AggregatorThread
Finished work by -- Thread-1 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-3 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-2 This is finished only after aggregator thread is done with it's work
Starting again the worker threads to see if cyclic barrier count gets reset
Starting --Thread-4
Starting --Thread-5
Starting --Thread-6
Added items --[4, 3, 2] by --Thread-4
Added items --[1, 0, 4] by --Thread-5
Added items --[0, 6, 2] by --Thread-6
Starting -- AggregatorThread Waiting for 4 secs to start working
Final result -- 64
Finished work by -- AggregatorThread
Finished work by -- Thread-6 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-4 This is finished only after aggregator thread is done with it's work
Finished work by -- Thread-5 This is finished only after aggregator thread is done with it's work
 */