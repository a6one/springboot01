package com.uplooking.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        Service service = new Service(latch);
        Runnable task = () -> service.exec();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }

        System.out.println("main thread await. " + latch.getCount());
        latch.await();
        System.out.println("main thread finishes await. " + latch.getCount());
    }


    public static class Service {
        private CountDownLatch latch;

        public Service(CountDownLatch latch) {
            this.latch = latch;
        }

        public void exec() {
            try {
                System.out.println(Thread.currentThread().getName() + " execute task. " + latch.getCount());
                sleep(2);
                System.out.println(Thread.currentThread().getName() + " finished task. " + latch.getCount());
            } finally {
                latch.countDown();
                System.out.println("latch.getCount" + latch.getCount());
            }
        }

        private void sleep(int seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
