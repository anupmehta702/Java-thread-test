package com.java.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LoginQueueUsingSemaphore {
    private final Semaphore semaphore;

    public LoginQueueUsingSemaphore( int slots) {
        this.semaphore = new Semaphore(slots);
    }

    public boolean tryLogin() throws InterruptedException {
        return semaphore.tryAcquire(2000, TimeUnit.MILLISECONDS);
    }
    public String logOut(){
        semaphore.release();
        return "Logged out";
    }

    public int availableSlots(){
        return semaphore.availablePermits();
    }

}
