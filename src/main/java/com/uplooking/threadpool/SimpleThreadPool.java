package com.uplooking.threadpool;

import java.util.LinkedList;

public class SimpleThreadPool {

    private final int size;

    private final static int DEFAULT_SIZE = 10;

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
    }

    private void init() {

    }

    private enum TaskState {
        FREE, RUNNING, BLICKED, DEAD
    }

    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return taskState;
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

        @Override
        public void run() {
            while (this.taskState != TaskState.DEAD){
                synchronized (TASK_QUEUE){
                    while (TASK_QUEUE.isEmpty()){

                    }
                }
            }

        }
    }
}
