package com.java.thread.customThreadPool;

import java.util.concurrent.BlockingQueue;

public class WorkerThread implements Runnable {
    BlockingQueue queue;
    int threadId = 1;

    public WorkerThread(BlockingQueue queue, int threadId) {
        this.queue = queue;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = (Task) queue.take();
                System.out.println("Worker Thread - " + threadId + " picks up task - " + task.taskId);
                task.run(); //using runUsingThreadPoolExecutor and not thread.start
            } catch (RuntimeException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
