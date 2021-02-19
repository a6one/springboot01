package com.uplooking.thread.pipeline;

import java.text.MessageFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PipelineDemo {
    /**
     * 上下文数据载体
     */
    public static class Context {
        public double B;
        double C;
        public double r;
        public String org;
    }


    static class Plus extends Thread {

        public static BlockingQueue<Context> bq = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            while (true) {
                try {
                    Context ctx = bq.take();
                    ctx.r = ctx.B + ctx.C;
                    Multiply.bq.add(ctx);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    static class Multiply extends Thread {

        public static BlockingQueue<Context> bq = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            while (true) {
                try {
                    Context ctx = bq.take();
                    ctx.r = ctx.B * ctx.r;
                    Div.bq.add(ctx);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class Div extends Thread {

        public static BlockingQueue<Context> bq = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            while (true) {
                try {
                    Context ctx = bq.take();
                    ctx.r = ctx.r / 2;
                    System.out.println(ctx.org + "=" + ctx.r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //启动线程之后等待数据
        new Plus().start();
        new Multiply().start();
        new Div().start();

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                Context ctx = new Context();
                ctx.B = i;
                ctx.C = j;
                ctx.org = MessageFormat.format("({0}+{1})*{0}/2", ctx.B, ctx.C);
                Plus.bq.add(ctx);
            }
            System.out.println("======================");
        }
    }

}

