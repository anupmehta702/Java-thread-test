package com.java.thread.cyclicBarrier;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class AggregatorThread implements Runnable{
    List<Integer> result;
    String threadName= "AggregatorThread";

    public AggregatorThread(List<Integer> result) {
        this.result = result;
    }

    @Override
    public void run() {
        System.out.println("Starting -- "+threadName+ " Waiting for 4 secs to start working");
        try {
            if(result.size() == 3) sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer finalResult = result.stream().reduce(0,(a,b)->a+b);
        System.out.println("Final result -- "+finalResult);
        System.out.println("Finished work by -- "+threadName);
    }
}
