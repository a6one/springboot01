package com.uplooking.thread;

public class ThreadLocalDemo {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "thread-local-string";
        }
    };

    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                THREAD_LOCAL.set("thread-variable:" + finalI);
                System.out.println("thread-variable:" + THREAD_LOCAL.get());
            }, "thread-name:" + i).start();
        }

        System.out.println(THREAD_LOCAL.get());

    }
}
