package com.uplooking.thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * schedule (TimerTask task, Date time) 延迟执行一次
 * schedule(TimerTack task, Date firstTime, long period) 每个多久执行一次
 */
public class TimeDemo {
    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "--" + i);
                i++;
            }
        }, TimeUnit.SECONDS.toSeconds(1000), TimeUnit.SECONDS.toSeconds(1000));//三个参数
    }

}
