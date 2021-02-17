package com.uplooking.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ProduceAndConsumerDemo {

    private static final AtomicInteger count = new AtomicInteger();

    private final Object OBJECT = new Object();
    private final Object LOCK_OBJECT = new Object();

    private static volatile boolean isProduced = false;


    /**
     * 生产者：p1->1
     * 当前等待的线程：p1
     * 当前等待的线程：p2
     * 消费者：c1->1            -> 激活了p1        wait:[p2]
     * 当前等待的线程：c2        -> c2 wait        wait:[p2,c2]
     * 生产者：p1->2           ->                wait:[c2]
     * 当前等待的线程：p1        -> p1 和 p2 都wait wait:[p1,c2]
     * 消费者：c1->2           -> 激活了 p2        wait:[p1]
     * 当前等待的线程：c1                          wait:[p1,c1]
     * 当前等待的线程：c2                          wait:[p1,c1,c2]
     * 生产者：p2->3                             wait:[c1,c2]
     * 当前等待的线程：p2                          wait:[p2,c1,c2]
     * 当前等待的线程：p1                          wait:[p1,p2,c1,c2]
     */
    public void producer() throws InterruptedException {
        synchronized (OBJECT) {
            if (isProduced) {
                // wait 会释放锁
                System.out.println("当前等待的线程：" + Thread.currentThread().getName());
                OBJECT.wait();      // 上锁的本质是什么？-> 给对象上锁(锁对象) -> 获取与锁对象相关联的monitor对象
                // 锁对象.wait()与当前线程的关系？锁对象等待 -> 促使当前线程进入等待状态
            } else {
                System.out.println("生产者：" + Thread.currentThread().getName() + "->" + count.incrementAndGet());
                OBJECT.notify();
                isProduced = true;
            }
        }
    }

    public synchronized void producerV2() throws InterruptedException {
        if (isProduced) {
            wait();     //this.wait() --> 此时的锁对象时this -> 获取this对象所关联的monitor的对象
        } else {
            System.out.println("生产者：" + count.incrementAndGet());
            notify();
            isProduced = true;
        }
    }

    public static synchronized void producerV3() throws InterruptedException {
        if (isProduced) {
            ProduceAndConsumerDemo.class.wait();
        } else {
            System.out.println("生产者：" + count.incrementAndGet());
            ProduceAndConsumerDemo.class.notify();
            isProduced = true;
        }
    }

    public void producerV4() throws InterruptedException {
        synchronized (OBJECT) {     // 未获取到锁的会进入到等待队列

            while (isProduced) {    // 条件变量
                OBJECT.wait();      // 条件队列
            }

            System.out.println("生产者：" + Thread.currentThread().getName() + "->" + count.incrementAndGet());
            OBJECT.notifyAll();
            isProduced = true;
        }

        if (!isProduced) {
            System.out.println("生产者没有获取到锁的" + Thread.currentThread().getName() + "->" + count.get());
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (OBJECT) {
            if (isProduced) {
                System.out.println("消费者：" + Thread.currentThread().getName() + "->" + count.get());
                OBJECT.notify();
                isProduced = false;
            } else {
                System.out.println("当前等待的线程：" + Thread.currentThread().getName());
                OBJECT.wait();
            }
        }
    }

    public synchronized void consumerV2() throws InterruptedException {
        if (isProduced) {
            System.out.println("消费者：" + count.get());
            notify();
            isProduced = false;
        } else {
            wait();
        }
    }

    public static synchronized void consumerV3() throws InterruptedException {
        if (isProduced) {
            System.out.println("消费者：" + count.get());
            ProduceAndConsumerDemo.class.notify();
            isProduced = false;
        } else {
            ProduceAndConsumerDemo.class.wait();
        }
    }

    public void consumerV4() throws InterruptedException {
        synchronized (OBJECT) {

            while (!isProduced) {// while 还会检查一遍逻辑条件
                OBJECT.wait();
            }

            System.out.println("消费者：" + Thread.currentThread().getName() + "->" + count.get());
            OBJECT.notifyAll();
            isProduced = false;

        }
        if (isProduced) {
            System.out.println("消费者没有获取到锁的" + Thread.currentThread().getName() + "->" + count.get());
        }
    }

    private void write() {
        synchronized (OBJECT) {
            count.incrementAndGet();//
            try {
                System.out.println("t1获取到锁");
                Thread.sleep(5000); //休眠5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("t1未获取到锁");
    }

    private void read() {
        synchronized (OBJECT) {      // 未获取到锁会释放锁，那么synchronize 中调用wait 是让获取到锁的线程进行等待
            System.out.println("t2获取到锁到锁");
            System.out.println(count.get());
        }
        System.out.println("t2未获取到锁到锁");
    }

    public static void main(String[] args) {
        ProduceAndConsumerDemo pc = new ProduceAndConsumerDemo();

        Thread t1 = new Thread(() -> {
            try {
                //当前线程一直去获取锁
                pc.write();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                //当前线程一直去获取锁
                for (;;)
                pc.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t2.start();
        t1.start();

    }


    // 同对象锁 同都要上锁 同一把锁
    public static void main01(String[] args) {
        ProduceAndConsumerDemo pc = new ProduceAndConsumerDemo();
        Stream.of("p1").forEach(s -> {
            Thread t = new Thread(() -> {
                try {
                    while (true)
                        pc.producerV4();     //当前线程一直去获取锁

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName(s);
            t.start();
        });


        Stream.of("c1").forEach(s -> {
            Thread t = new Thread(() -> {
                try {
                    for (; ; )
                        pc.consumerV4();    //当前线程一直去获取锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName(s);
            t.start();
        });

    }
}
