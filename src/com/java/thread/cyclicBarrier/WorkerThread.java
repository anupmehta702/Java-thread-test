package com.java.thread.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class WorkerThread implements Runnable {
    CyclicBarrier cyclicBarrier;
    String threadName;
    List<Integer> result;
    Random random = new Random();

    public WorkerThread(CyclicBarrier cyclicBarrier, int threadName,List<Integer> result) {
        this.cyclicBarrier = cyclicBarrier;
        this.threadName = "Thread-"+threadName+" ";
        this.result=result;
    }

    @Override
    public void run() {
        System.out.println("Starting --"+threadName);
        List<Integer> partialResult = new ArrayList<>();
        IntStream.range(1,4)
                .forEach((index)->{
                    partialResult.add(random.nextInt(10));});
        System.out.println("Added items --"+partialResult+" by --"+threadName);
        result.addAll(partialResult);
        try {
            cyclicBarrier.await();
            System.out.println("Finished work by -- "+threadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
