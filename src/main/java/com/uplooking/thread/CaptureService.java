package com.uplooking.thread;

import java.util.*;

public class CaptureService {

    private static final LinkedList<Control> CONTROLS = new LinkedList<>();

    private static final int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .stream()
                .map(CaptureService::createThread)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });

        worker.forEach(w -> {
            try {
                w.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("all the worker is end " + Thread.currentThread().getName()).ifPresent(System.err::println);
    }

    // 如何让多个线程同时工作？不加锁，
    // 1、不加锁   2、入口处加锁判断 + 加锁和工作分离
    private static Thread createThread(String name) {
        return new Thread(() -> {
            Optional.of("begin worker is thread " + Thread.currentThread().getName()).ifPresent(System.out::println);

            synchronized (CONTROLS) {              // 一个线程获取到锁，其他线程都阻塞了
                Optional.of("owner worker is thread " + Thread.currentThread().getName()).ifPresent(System.out::println);
                while (CONTROLS.size() >= MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 获取到锁后，添加到链表末端
                CONTROLS.addLast(new Control());
            }

            // 这里一个主要的点是：这里是允许有多个线程进行操作，只需要在入口处限流线程
            Optional.of("working is thread " + Thread.currentThread().getName()).ifPresent(System.out::println);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS) {
                Optional.of("end worker is thread " + Thread.currentThread().getName()).ifPresent(System.out::println);
                CONTROLS.removeFirst();   // 不是线程安全的
                CONTROLS.notifyAll();
            }

        }, name);
    }

    private static class Control {
    }

}
