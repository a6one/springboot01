package com.uplooking.thread;

import reactor.core.publisher.Mono;

/**
 * volatile：用途 主线程中数据与子线程中数据共享
 */
public class ThreadDemo extends Thread {
    volatile boolean tag = true;

    public void run() {

        System.out.println("线程开始执行。。。");
        while (tag) {

        }
        System.out.println("线程执行结束。。。");
    }

    public void isRun(boolean tag) {
        this.tag = tag;
    }

    public static void main1(String[] args) throws InterruptedException {

        ThreadDemo t1 = new ThreadDemo();
        t1.start();
        Thread.sleep(300);
        t1.isRun(false);

    }

    public static void main(String[] args) {
        Mono.just("mono 第一次测试").then(Mono.fromRunnable(() -> {
            System.out.println("mono from runnable()");
        })).subscribe(System.out::println);

        Mono<Void> mono1 = Mono.fromRunnable(() -> {
            System.out.println("sleep1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("mono1");
        });
        Mono<Void> mono2 = Mono.fromRunnable(() -> {
            System.out.println("mono2");
        });
        Mono<Void> mono3 = Mono.fromRunnable(() -> {
            System.out.println("mono3");
        });

    }

}
