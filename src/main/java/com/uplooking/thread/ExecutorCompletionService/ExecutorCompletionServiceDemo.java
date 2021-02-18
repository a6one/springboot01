package com.uplooking.thread.ExecutorCompletionService;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class ExecutorCompletionServiceDemo {

    public static void main(String[] args) {
        CompletionService<String> service = new ExecutorCompletionService<>(Executors.newSingleThreadExecutor());
    }
}
