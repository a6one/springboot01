package com.uplooking.thread.serial;

import java.util.concurrent.*;

public class MessageFileDownloader {

    /**
     * 对任务处理的抽象
     *
     * @param <T> 任务的类型
     * @param <V> 任务处理结果的类型
     * @author huzhiqiang
     */
    interface TaskProcssor<T, V> {
        V doProcess(T task) throws Exception;
    }

    static class TerminatableWorkerThread<T, V> extends Thread {
        private final BlockingQueue<Runnable> workQueue;

        //负责正真执行任务的对象
        private final TaskProcssor<T, V> taskProcssor;

        public TerminatableWorkerThread(BlockingQueue<Runnable> workQueue, TaskProcssor<T, V> taskProcssor) {
            super();
            this.workQueue = workQueue;
            this.taskProcssor = taskProcssor;
        }

        Future<V> submit(final T task) throws InterruptedException {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return taskProcssor.doProcess(task);
                }
            };

            FutureTask<V> ft = new FutureTask<V>(callable);
            workQueue.put(ft);

//            terminationToken.reservations.incrementAndGet();
            return ft;
        }

        @Override
        public void run()  {
            Runnable ft = null;
            try {
                ft = workQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                ft.run();
            } finally {
//                terminationToken.reservations.decrementAndGet();
            }
        }
    }

    static abstract class AbstractSerializer<T, V> {
        private final TerminatableWorkerThread<T, V> workerThread;

        public AbstractSerializer(BlockingQueue<Runnable> workQueue, TaskProcssor<T, V> taskProcssor) {
            super();
            this.workerThread = new TerminatableWorkerThread<>(workQueue, taskProcssor);
        }

        /**
         * 留给子类实现。用于根据指定参数生成相应的任务实例
         *
         * @param params
         * @return
         */
        public abstract T makeTask(Object... params);

        /**
         * 对外暴露的方法
         *
         * @param params
         * @return
         * @throws InterruptedException
         */
        public Future<V> service(Object... params) throws InterruptedException {
            T task = makeTask(params);
            Future<V> resultPromise = workerThread.submit(task);
            return resultPromise;
        }

        public void init() {
            workerThread.start();
        }

        public void shutdown() {
//            workerThread.terminate();
        }
    }

    static class Main {

        public static void main(String[] args) throws InterruptedException, ExecutionException {
            SomeService ss = new SomeService();
            ss.init();

            Future<String> result = ss.doSomething("serial Thread confinement", 1);

            Thread.sleep(50);
            System.out.println(result.get());

            ss.shutdown();
        }

        private static class Task {
            private final int id;
            private final String message;

            public Task(int id, String message) {
                this.id = id;
                this.message = message;
            }
        }

        private static class SomeService extends AbstractSerializer<Task, String> {

            public SomeService() {
                super(new ArrayBlockingQueue<>(100), new TaskProcssor<Task, String>() {
                    @Override
                    public String doProcess(Task task) throws Exception {
                        System.out.println("[" + task.id + "]: " + task.message);
                        return task.message + " accepted.";
                    }
                });
            }

            @Override
            public Task makeTask(Object... params) {
                String message = (String) params[0];
                int id = (int) params[1];
                return new Task(id, message);
            }

            public Future<String> doSomething(String message, int id) throws InterruptedException {
                Future<String> result = null;
                result = service(message, id);
                return result;
            }
        }
    }
}
