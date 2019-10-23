package com.uplooking.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyCountDownLatch {
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    private static Executor service = Executors.newFixedThreadPool(5);


    public static void main(String[] args) {


        //System.out.println("主线程：" + Thread.currentThread().getName() + "---" + countDownLatch.getCount());

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    Thread.sleep(2000);
                } catch (Exception ignored) {

                } finally {
                    countDownLatch.countDown();
                }
            }).start();

        }

        System.out.println("堵塞主线程：" + Thread.currentThread().getName() + "---" + countDownLatch.getCount());
        try {
            //单词await
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("释放主线程：" + Thread.currentThread().getName() + "---" + countDownLatch.getCount());

    }

}


