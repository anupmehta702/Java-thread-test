package com.java.thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class TestSemaphore {
    public static void main(String[] args) {
        int slot = 2;
        int threadNumber = 4;
        LoginQueueUsingSemaphore login = new LoginQueueUsingSemaphore(slot);
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        IntStream.range(0, threadNumber).
                forEach((index) -> {
                    executor.execute(() -> {
                        try {
                            System.out.println("User - " + index + " login status -" + login.tryLogin());
                            Thread.sleep(1000);
                            System.out.println("User - " + index + " " + login.logOut());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                });
        executor.shutdown();
        int size =1 << 16;
        System.out.println("Print 1 << 16 -- "+size);
    }
}
/*
Output -
we have permission for 2 users .Every user logs out after 1 sec.
user 0 and 1 get permission and login while 2 and 3 are waiting for the permission since we have
put acquire wait time of 2 secs
User - 0 login status -true
User - 1 login status -true
User - 0 Logged out
User - 3 login status -true
User - 3 Logged out
User - 2 login status -true
User - 2 Logged out
User - 1 Logged out
 */
