package com.java.thread.CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CASExample {
    public static AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) {
        testAtomicInt();
    }

    public static void testAtomicInt() {
        boolean isSuccess = atomicInt.compareAndSet(0, 100);
        System.out.println("is CAS operation successful - " + isSuccess);//true
        isSuccess = atomicInt.compareAndSet(10, 101);
        System.out.println("is CAS operation successful - " + isSuccess);//false
        boolean isCASOperationSuccess = false;
        while (!isCASOperationSuccess) {
            int oldValue = atomicInt.intValue();
            isCASOperationSuccess = atomicInt.compareAndSet(oldValue, 100);
        }

    }
}
