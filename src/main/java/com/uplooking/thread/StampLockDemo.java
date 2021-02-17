package com.uplooking.thread;

import java.util.concurrent.locks.StampedLock;

public class StampLockDemo {

    private static final StampedLock STAMPED_LOCK = new StampedLock();

    public static void main(String[] args) {

        long optimisticRead = STAMPED_LOCK.tryOptimisticRead();
        if (STAMPED_LOCK.validate(optimisticRead)) {
            optimisticRead = STAMPED_LOCK.readLock();
            try {
                System.out.println("获取到了读锁");
            } finally {
                STAMPED_LOCK.unlock(optimisticRead);
            }
        }

    }

}
