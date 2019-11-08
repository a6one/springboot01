package com.uplooking.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchange用于多个线程之间进行数据交换
 */
public class ExchangerDemo {

    // 超市结账服务台（同步点）
    private static final Exchanger<String> exgr = new Exchanger<String>();
    // 你和你女朋友
    private static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        System.out.println("逛街开始，行动！！！");
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("你进入超市");
                try {
                    String u = "电子产品";
                    System.out.println("你购买ok，到达服务台");
                    // 到达同步点，等待或者交换（所有线程都到达）
                    String exchange = exgr.exchange(u);
                    System.out.println("exchange:" + exchange);
                } catch (InterruptedException e) {
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("女朋友进入超市");
                try {
                    String girlfriend = "化妆品";
                    // 交换购买物品   到达同步点，等待或者交换（所有线程都到达）
                    String u1 = exgr.exchange("girlfriend");
                    System.out.println("女朋友到达服务台，交换购买物品");
                    System.out.println("你购买的是：" + u1 + ",女朋友购买的是：" + girlfriend);
                } catch (InterruptedException e) {
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("mmmm朋友进入超市");
                try {
                    String sifajing = "洗发精";
                    // 交换购买物品   到达同步点，等待或者交换（所有线程都到达）
                    String u1 = exgr.exchange("sifajing");
                    System.out.println("女朋友到达服务台，交换购买物品");
                    System.out.println("你购买的是：" + u1 + ",女朋友购买的是：" + sifajing);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.shutdown();
    }
}
