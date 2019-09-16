package com.java.thread.customThreadPool;

import static java.lang.Thread.sleep;

public class Task implements Runnable {
    Integer taskId =1;

    public Task(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {

        try {
            System.out.println("Implementing task - "+taskId + " Sleeping for 2 secs ");
            sleep(2000);
            System.out.println("Finished performing task "+taskId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
