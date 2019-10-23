package com.uplooking.cgilb;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BookFacadeProxyCglib implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object invoke = methodProxy.invokeSuper(o, args);
        return invoke;
    }

    //泛型方法，注意泛型的写法
    public <T> T getProxyInstance(Class<T> target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(this);//显示回调
        return (T) enhancer.create(); //创建代理对象
    }
}
