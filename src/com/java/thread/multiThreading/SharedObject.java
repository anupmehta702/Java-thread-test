package com.java.thread.multiThreading;

public class SharedObject {
    Integer count = 0;
    Integer maxCount = 10;

    public SharedObject(Integer count, Integer maxCount) {
        this.count = count;
        this.maxCount = maxCount;
    }

    boolean isCountLessThanMaxCount() {
        return count <= maxCount ? true : false;
    }

}
