package com.uplooking.thread;

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

    public static void main(String[] args) throws InterruptedException {

        ThreadDemo t1 = new ThreadDemo();
        t1.start();
        Thread.sleep(300);
        t1.isRun(false);

    }

}
