package com.uplooking.cgilb;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BookFacadeProxy implements InvocationHandler {

    //代理对象
    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象执行前置方法");
        Object invoke = method.invoke(target, args);
        System.out.println("代理对象执行后置方法");
        return invoke;
    }

    public Object getProxyInstance(Object target) {
        this.target = target;
        //代理对象的加载器 + 代理对象的接口
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
