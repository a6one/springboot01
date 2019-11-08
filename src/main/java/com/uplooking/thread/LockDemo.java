package com.uplooking.thread;


import java.util.concurrent.locks.*;

/**
 * 在等待/通知模式中，同一把锁很重要
 * wait就相当于Condition中的await
 * notify就相当于Condition中的signal
 * notifyAll就相当于signalAll
 */
public class LockDemo {

    static class MyThread1 extends Thread {
        private Lock lock; //synchronized
        private Condition condition; //wait() | notify()

        public MyThread1(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
            LockSupport.park(); //锁定当前线程
            LockSupport.unpark(Thread.currentThread());//释放当前线程
        }

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("线程开始工作" + Thread.currentThread().getName());
                condition.await(); //加入到等待队列中
                System.out.println("线程停止工作" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class OtherThread1 extends Thread {
        private Lock lock;
        private Condition condition;

        public OtherThread1(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("线程开始执行" + Thread.currentThread().getName());
            condition.signal();
            System.out.println("线程停止执行" + Thread.currentThread().getName());
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        MyThread1 myThead1 = new MyThread1(lock, condition);
        myThead1.start();
        myThead1.setName("线程A");

        /**ReentrantReadWriteLock**/
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        //获取读锁
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        //获取写🔐
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    }
}

