package com.uplooking.CompletableFuture;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@Log4j2
public class MyCompletableFuture {

    private Executor executor = Executors.newFixedThreadPool(10);

//    public void test() {
//        //创建一个异步的操作，操作完后进行消费，和记录是否含有报错信息
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("result:" + 10);
//            return 10;
//        }, executor).thenAccept(x -> x.intValue()).whenComplete((o, e) -> log.error(e.getMessage()));
//
//        Void join = CompletableFuture.allOf(voidCompletableFuture).join();
//    }

    public static void main01(String[] args) throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.join();
        //future.get();
    }

    public static void main(String[] args) throws Exception{
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
//            int i =1/0;
            try {
                System.out.println("===========f1==========");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
//        CompletableFuture.allOf(f1); //这样不会阻塞
        CompletableFuture.allOf(f1).thenRun(()->{
            System.out.println("=============thenRun==============");
        }).join(); //这样才会进行阻塞

//        Thread.sleep(6000);
        System.out.println("CompletableFuture Test");


        Function<Object, String> func = Functions.forSupplier(new Supplier<String>() {
            @Override
            public String get() {
                return "hello world";
            }
        });

        HashCode hash = Files.asByteSource(new File("")).hash(Hashing.sha256());
        System.out.println(hash);

    }

}
