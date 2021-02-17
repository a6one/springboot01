package com.uplooking.thread.threadpool;

import java.util.concurrent.*;

public class ThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<String> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("线程池中的方法");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("异常了");
        });


        System.out.println("执行future...");
        try {
            System.out.println("future:" + future.get());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("future.isCancelled--->" + future.isCancelled());
            System.out.println("future.isDone--->" + future.isDone());
        }



    }
}
