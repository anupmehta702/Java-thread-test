package com.java.thread.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class TestCyclicBarrier {

    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        CyclicBarrier barrier = new CyclicBarrier(3,new AggregatorThread(input));
        IntStream.range(1,4)
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
Finished work by -- Thread-2
Finished work by -- Thread-3
Finished work by -- Thread-1
 */