package com.uplooking.thread;

import java.util.Collection;

public interface Lock {

    class TimeoutException extends Exception {
        public TimeoutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws TimeoutException, InterruptedException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();

}
