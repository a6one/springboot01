package com.uplooking.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 总结就是：当前提交任务数大于（maxPoolSize + queueCapacity）时就会触发线程池的拒绝策略了
 */
public class ThreadPoolExecutorDemo {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            2,
            3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1));

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "====" + finalI);
            });
        }
    }
}
