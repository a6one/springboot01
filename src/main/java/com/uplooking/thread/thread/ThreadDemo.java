package com.uplooking.thread.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo {

    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static CountDownLatch latch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.incrementAndGet();
                    latch.countDown();
                }

            }).start();
        }

        latch.await();
        System.out.println(atomicInteger.get());
    }

}
