package com.uplooking.thread;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaseDemo {

    public static void main(String[] args) {
        final Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("当前阶段是 phase :" + phase + "总的线程数量：" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };

        for (int i = 0; i < 5; i++) {
            new Task(phaser).start();
        }

        phaser.register(); //registeredParties
        System.out.println("The worker [" + Thread.currentThread().getName() + "] is working ...");
        //phaser.arriveAndAwaitAdvance(); // 每个线程调用 arriveAndAwaitAdvance 会立即阻塞，知道有registeredParties个线程调用了 arriveAndAwaitAdvance 方法后，才会进入下一个阶段
        //phaser.arriveAndDeregister(); // 抵达 然后减少线程
        //phaser.arrive(); //非阻塞
        //需要指定一个phase数字，表示此Thread阻塞,直到phase推进到此周期
        phaser.awaitAdvance(phaser.getPhase()); // 不会添加到arriveParties 中进行统计
        // 如果传入参数phase值和当前getPhase()方法返回值一样，则在屏障处等待，否则继续向下面运行，有些类似于【旁观者】的作用，当观察的条件满足了就等待（旁观），如果条件不满足，则程序向下继续运行
        System.out.println("all of worker finished the task .");

    }

    static class Task extends Thread {
        private final Phaser phaser;

        private Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register(); //添加自己
        }

        @Override
        public void run() {

            System.out.println("The worker once [" + getName() + "] is working ...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            phaser.arriveAndAwaitAdvance();


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The worker two [" + getName() + "] is working ...");
            phaser.arriveAndAwaitAdvance();

        }
    }
}
