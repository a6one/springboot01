package com.uplooking.jvm;

public class JvmDemo {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.getClass().getClassLoader());

        JvmDemo d = new JvmDemo();
        System.out.println(d.getClass().getClassLoader());


        //===================jvm============================
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024/1024);
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024/1024);
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
