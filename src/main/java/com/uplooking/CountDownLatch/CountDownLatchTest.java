package com.uplooking.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest extends Thread {

    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {

            new CountDownLatchTest().start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished");

    }

    public void run() {

        try {

            System.out.println(Thread.currentThread().getName() + " waiting");
            Thread.sleep(2000);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            countDownLatch.countDown();

        }

    }
}
