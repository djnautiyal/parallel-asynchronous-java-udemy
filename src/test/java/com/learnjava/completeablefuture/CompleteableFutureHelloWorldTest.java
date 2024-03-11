package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class CompleteableFutureHelloWorldTest {
    HelloWorldService hws = new HelloWorldService();
    CompleteableFutureHelloWorld completeableFutureHelloWorld = new CompleteableFutureHelloWorld(hws);

    @Test
    void helloWorld() {
        CompletableFuture<String> completableFuture = completeableFutureHelloWorld.helloWorld();

        completableFuture.thenAccept((s) ->{
            assertEquals("HELLO WORLD",s);
                })
                .join();

    }

    @Test
    void helloWorldWithSize() {
        CompletableFuture<String> completableFuture = completeableFutureHelloWorld.helloWorldWithSize();

        completableFuture.thenAccept((s) -> assertEquals("11 - HELLO WORLD", s))
                .join();
    }

    @Test
    public void helloWorldCombineWithoutAsync(){
        String string = completeableFutureHelloWorld.helloWorldCombineWithoutAsync();
        assertEquals("HELLO WORLD!", string);
    }
    @Test
    public void helloWorldCombineWithAsync(){
        String string = completeableFutureHelloWorld.helloWorldCombineWithAsync();
        assertEquals("HELLO WORLD!", string);
    }
    @Test
    public void helloWorldCombineWith3AsyncCalls(){
        String string = completeableFutureHelloWorld.helloWorldCombineWith3AsyncCalls();
        assertEquals("HELLO WORLD!GENERAL KENOBI!", string);
    }

    @Test
    public void helloWorldCombineWith3AsyncCallsAndCustomThreadpool(){
        String string = completeableFutureHelloWorld.helloWorldCombineWith3AsyncCallsAndCustomThreadpool();
        assertEquals("HELLO WORLD!GENERAL KENOBI!", string);
    }

    @Test
    public void helloWorldCombineWith3AsyncCallsAndCustomThreadpoolAndOverloadedAsyncFunctions(){
        String string = completeableFutureHelloWorld.helloWorldCombineWith3AsyncCallsAndCustomThreadpoolAndOverloadedAsyncFunctions();
        assertEquals("HELLO WORLD!GENERAL KENOBI!", string);
    }

    @Test
    public void helloWorldCombineWith4AsyncCalls(){
        String string = completeableFutureHelloWorld.helloWorldCombineWith4AsyncCalls();
        assertEquals("HELLO WORLD!GENERAL KENOBI!YOU ARE A BOLD ONE.", string);
    }

    @Test
    void helloWorldWithThenCompose() {
        startTimer();
        CompletableFuture<String> completableFuture = completeableFutureHelloWorld.helloWorldWithThenCompose();

        completableFuture.thenAccept((s) ->{
                    assertEquals("HELLO WORLD!",s);
                })
                .join();

        timeTaken(); stopWatchReset();
    }


}