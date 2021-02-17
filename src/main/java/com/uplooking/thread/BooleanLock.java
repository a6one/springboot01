package com.uplooking.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class BooleanLock implements Lock {

    private boolean initValue;

    private Collection<Thread> threadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    // one thread lock other thread blocked api层面的 显示的获取和释放锁？
    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue) {
            threadCollection.add(Thread.currentThread());
            this.wait(); //block  release lock wait还是要去抢占cpu执行权
        }

        threadCollection.remove(Thread.currentThread());
        initValue = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws TimeoutException, InterruptedException {
        if (mills < 0) {
            lock();
        }
        long hashRemaining = mills;
        long endTime = System.currentTimeMillis() + mills; // 到什么时候超时
        while (initValue) {
            if (hashRemaining <= 0) {
                throw new TimeoutException("time out");
            }
            threadCollection.add(Thread.currentThread());
            // 等待的时间
            this.wait(mills); //释放了还会去抢占cpu执行权
            hashRemaining = System.currentTimeMillis() - endTime;
        }

        this.initValue = true;
        this.currentThread = Thread.currentThread();

    }

    @Override
    public synchronized void unlock() {
        if (currentThread == Thread.currentThread()) {
            initValue = false;
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(threadCollection); // 不可变性，防止被修改    private final static unmodifiableXXX
    }

    @Override
    public int getBlockedSize() {
        return threadCollection.size();
    }


    public static void main(String[] args) {
        Lock lock = new BooleanLock();

        Stream.of("T1", "T2", "T3", "T4").forEach(name -> {
            new Thread(() -> {
                try {
                    lock.lock();
                    System.out.println("获取到了锁：" + name);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("释放了锁：" + name);
                }
            }, name).start();
        });
    }
}
