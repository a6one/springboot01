package com.uplooking.thread;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class JoinDemo {

    public static void main01(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("thread is running");
                Thread.sleep(10000);
                System.out.println("thread is done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t1.join(1000);

        Optional.of("All of tasks finish done ").ifPresent(System.out::println);

        Thread.currentThread().join(); // 阻塞主线程

        IntStream.range(1, 1000)
                .forEach(i -> System.out.println(Thread.currentThread().getName() + "-->" + i));


    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println();
                }
            }
        });

        t1.start();
        t1.setUncaughtExceptionHandler((thread, e) -> {
            // thread 中为捕获的异常
        });
        Arrays.stream(t1.getStackTrace()).filter(StackTraceElement::isNativeMethod).forEach(System.out::print);

        Thread.sleep(100);
        System.out.println(t1.isInterrupted());
        t1.interrupt(); //标记中断
        System.out.println(t1.isInterrupted());

    }
}
