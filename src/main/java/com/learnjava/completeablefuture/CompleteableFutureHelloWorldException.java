package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.CommonUtil.stopWatchReset;
import static com.learnjava.util.LoggerUtil.log;

public class CompleteableFutureHelloWorldException {
    private final HelloWorldService helloWorldService;

    private BiFunction<String, Throwable, String> exceptionStringBiFunction = (string, exception) -> {
        log("string received is: " + string);
        if (exception != null) {
            log("Exception is: " + exception.getMessage());
            return "";
        }
        return string;
    };

    private Function<Throwable, String> throwableStringFunction = exception -> {
        log("Exception is: " + exception.getMessage());
        return "";
    };

    private BiConsumer<String, Throwable> throwableBiConsumer = (string, exception) ->{
      log("String received is: " + string);
        if (exception != null)
            log("Exception is: " + exception.getMessage());
    };

    CompleteableFutureHelloWorldException(HelloWorldService helloWorldService){
        this.helloWorldService= helloWorldService;
    }

    public String helloWorldCombineWith3AsyncCallsAndHandleMethod(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        });

        String result = hello
                .handle(exceptionStringBiFunction)
                .thenCombine(world, (x,y) -> x+y)
                .handle(exceptionStringBiFunction)
                .thenCombine(kenobi,(x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

    public String helloWorldCombineWith3AsyncCallsAndExceptionallyMethod(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        });

        String result = hello
                .exceptionally(throwableStringFunction)
                .thenCombine(world, (x,y) -> x+y)
                .exceptionally(throwableStringFunction)
                .thenCombine(kenobi,(x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

    public String helloWorldCombineWith3AsyncCallsAndWhenCompleteMethod(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> kenobi = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "General Kenobi!";
        });

        String result = hello
                .whenComplete(throwableBiConsumer)
                .thenCombine(world, (x,y) -> x+y)
                .whenComplete(throwableBiConsumer)
                .thenCombine(kenobi,(x,y) -> x+y)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken(); stopWatchReset();
        return result;
    }

}
