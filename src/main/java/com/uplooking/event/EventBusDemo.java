package com.uplooking.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

public class EventBusDemo {


    public static void main(String[] args) {

         EventBus bus = new EventBus(new SubscriberExceptionHandler() {

            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                // 异常的拦截处理
                System.out.println("==event==" + context.getEvent());
                System.out.println("==subscriber class==" + context.getSubscriber());
                System.out.println("==subscriber method==" + context.getSubscriberMethod());
            }
        });

        bus.register(new StringSubscrible());
        bus.post("StringSubscrible send first ");
        bus.post(123);


    }
}
