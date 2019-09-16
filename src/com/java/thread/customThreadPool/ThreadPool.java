package com.java.thread.customThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private List<Thread> workerThreads = new ArrayList<>();

    public ThreadPool(int maxNoOfThreads) {
        for (int i = 0; i < maxNoOfThreads; i++) {
            workerThreads.add(new Thread(new WorkerThread(taskQueue, i)));
        }
        for (Thread workerThread : workerThreads) {
            workerThread.start();
        }
    }

    public void submit(Task task) {
        taskQueue.add(task); //internally BlockingQueue checks if it can add the task
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2);
        threadPool.submit(new Task(1));
        threadPool.submit(new Task(2));
        threadPool.submit(new Task(3));
        threadPool.submit(new Task(4));
        threadPool.submit(new Task(5));
    }
}

/*
Output -
Worker Thread - 0 picks up task - 1
Worker Thread - 1 picks up task - 2
Implementing task - 2 Sleeping for 2 secs
Implementing task - 1 Sleeping for 2 secs
 Finished performing task 1
Worker Thread - 0 picks up task - 3
Finished performing task 2
Worker Thread - 1 picks up task - 4
Implementing task - 3 Sleeping for 2 secs
Implementing task - 4 Sleeping for 2 secs
Finished performing task 3
Worker Thread - 0 picks up task - 5
Finished performing task 4
Implementing task - 5 Sleeping for 2 secs
Finished performing task 5
 */
