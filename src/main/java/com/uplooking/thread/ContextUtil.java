package com.uplooking.thread;

public class ContextUtil {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "contextUtil-thread-local";
        }
    };

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static ThreadLocal<String> getThreadLocal() {
        return THREAD_LOCAL;
    }

    /**
     * 单例和工具类的区别？
     * 单例：当前类被实例化一次后不再进行实例化
     * 工具类：
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(new ContextUtil().get() + "---->" + new ContextUtil().getThreadLocal()); // threadLocal是单例
            }, "context-" + i).start();
        }

    }
}
