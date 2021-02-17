package com.uplooking.thread.phaser;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserDemo {


    public static void main(String[] args) {
        Phaser phaser = new Phaser(3); //此处可使用CountDownLatch(1)
        for (int i = 0; i < 3; i++) {
            new MyThread(i, phaser).start();
        }

        System.out.println("主线程：[ " + Thread.currentThread().getName() + " ] phase :" + phaser.getPhase() + " parties:" + phaser.getRegisteredParties());

        phaser.awaitAdvance(phaser.getPhase()); //此处可使用latch.await() , 检查这个阶段 的线程是否全部上报了，没有这个阶段就不会阻塞

        System.out.println("所有线程已经结束：[ " + Thread.currentThread().getName() + " ]");

        Executors.newWorkStealingPool();
    }
}


class MyThread extends Thread {
    private int c;
    private Phaser phaser;

    public MyThread(int c, Phaser phaser) {
        this.c = c;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        if (c == 1) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        phaser.arrive();  //此处可使用latch.countDown()
        System.out.println("当前到达的线程：[ " + c + "-->" + Thread.currentThread().getName() + " ] phase :" + phaser.getPhase());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
