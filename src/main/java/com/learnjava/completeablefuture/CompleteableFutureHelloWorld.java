package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.*;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompleteableFutureHelloWorld {
    private final HelloWorldService helloWorldService;

    CompleteableFutureHelloWorld(HelloWorldService helloWorldService){
        this.helloWorldService= helloWorldService;
    }

    CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
//                .thenAccept((x) -> {
//                    log("Result is: " + x);
//                });
    }

    CompletableFuture<String> helloWorldWithSize(){
        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())
                .thenApply(String::toUpperCase)
                .thenApply((s) -> s.length() + " - " + s);
    }

    public String helloWorldCombineWithoutAsync(){
        startTimer();
        String hello = helloWorldService.hello().toUpperCase();
        String world = helloWorldService.world().toUpperCase();

        String result = hello+world;

        timeTaken();stopWatchReset();
        return result;
    }

    public String helloWorldCombineWithAsync(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        String result = hello
                .thenCombine(world, (x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;

    }

    public String helloWorldCombineWith3AsyncCalls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        });

        String result = hello
                .thenCombine(world, (x,y) -> x+y)
                .thenCombine(kenobi,(x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

    public String helloWorldCombineWith3AsyncCallsAndCustomThreadpool(){
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        }, executorService);

        String result = hello
                .thenCombine(world, (x,y) -> {
                    log("Hello world!");
                    return x+y;
                })
                .thenCombine(kenobi,(x,y) -> {
                    log("Hello world! General kenobi!");
                    return x+y;
                })
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

    public String helloWorldCombineWith3AsyncCallsAndCustomThreadpoolAndOverloadedAsyncFunctions(){
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        }, executorService);

        String result = hello
                .thenCombineAsync(world, (x,y) -> {
                    log("Hello world!");
                    return x+y;
                }, executorService)
                .thenCombineAsync(kenobi,(x,y) -> {
                    log("Hello world! General kenobi!");
                    return x+y;
                }, executorService)
                .thenApplyAsync(s -> {
                    log("HELLO WORLD! GENERAL KENOBI!");
                    return s.toUpperCase();
                }, executorService)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

    public String helloWorldCombineWith4AsyncCalls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        });
        CompletableFuture<String> boldOne = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "You are a bold one.";
        });

        String result = hello
                .thenCombine(world, (x,y) -> x+y)
                .thenCombine(kenobi, (x,y) -> x+y)
                .thenCombine(boldOne, (x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return result;
    }

    CompletableFuture<String> helloWorldWithThenCompose(){
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose(helloWorldService::worldFuture)
                .thenApply(String::toUpperCase);
    }

    public String anyOf(){
        CompletableFuture<String> db = CompletableFuture.supplyAsync(() ->{
            delay(4000);
            log("response from DB");
            return "hello world";
        });

        CompletableFuture<String> rest = CompletableFuture.supplyAsync(() ->{
            delay(2000);
            log("response from rest call");
            return "hello world";
        });

        CompletableFuture<String> soap = CompletableFuture.supplyAsync(() ->{
            delay(3000);
            log("response from soap call");
            return "hello world";
        });


        List<CompletableFuture<String>> cfList = List.of(db,rest,soap);
        CompletableFuture<Object> cfAnyOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));

        String result = (String)cfAnyOf.thenApply(v ->{
            if(v instanceof  String)
                return v;

            return null;
        }).join();
        return result;
    }


    public static void main(String[] args) {
        HelloWorldService hws = new HelloWorldService();
        CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept((x) -> {
                    log("Result is: " + x);
                });


        log("Done!");
        delay(2000);
    }
}
