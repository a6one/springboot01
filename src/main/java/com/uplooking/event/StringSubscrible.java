package com.uplooking.event;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class StringSubscrible {

    @Subscribe
    public void subscrible1(String event) {
        System.out.println("=============subscrible1================" + event);
        throw new RuntimeException(event);
    }


    @Subscribe
    public void subscrible2(String event) {
        System.out.println("=============subscrible2================" + event);
    }

    @Subscribe
    public void subscrible3(String event) {
        System.out.println("=============subscrible3================" + event);
    }

    @Subscribe
    public void subscrible4(DeadEvent event) {
        System.out.println("=============subscrible4================" + event);
        System.out.println("dealEvent:" + event.getEvent() + "--eventSource:" + event.getSource());
    }
}
