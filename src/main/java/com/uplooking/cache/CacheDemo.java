package com.uplooking.cache;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

public class CacheDemo {
    public static void main(String[] args) {

        Cache<String, Object> cache = CacheBuilder.newBuilder().initialCapacity(10)
                .concurrencyLevel(6) //并发度，可以同时写缓存的线程数
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .expireAfterWrite(1, TimeUnit.MINUTES)   //设置写缓存后，1分钟过期
                //是在指定时间内没有被创建/覆盖，则指定时间过后，再次访问时，会去刷新该缓存，在新值没有到来之前，始终返回旧值。
                //刷新方法在LoadingCache接口的refresh()声明，实际最终调用的是CacheLoader的reload()
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)    //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .recordStats()
                //按权重来回收 CacheBuilder.maximumSize(long)，CacheBuilder.maximumWeight(long)是互斥的，只能二选一
                .maximumWeight(100).weigher(new Weigher<String, Object>() {
                    @Override
                    public int weigh(String key, Object value) {
                        return 0;
                    }
                })
                .removalListener(new RemovalListener<String, Object>() { //设置缓存的移除通知
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> notification) {
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                .build(new CacheLoader<String, Object>() {       //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });

    }
}
