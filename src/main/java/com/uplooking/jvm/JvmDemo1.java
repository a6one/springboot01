package com.uplooking.jvm;

/**
 * jhat：select s from java.lang.String s where s.value.length>=10000
 *
 * gc配置：
 * -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -Xmx256m -XX:+PrintGCDetails
 *  XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC Xloggc:F://test//gc.log
 */
public class JvmDemo1 {

    public static void main(String[] args) throws Exception {
        Thread.sleep(Integer.MAX_VALUE);
    }

}
