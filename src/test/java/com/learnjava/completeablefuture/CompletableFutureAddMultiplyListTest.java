package com.learnjava.completeablefuture;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

 class CompletableFutureAddMultiplyListTest {
    CompletableFutureAddMultiplyList completableFutureAddMultiplyList = new CompletableFutureAddMultiplyList();
    @Test
    void concatenateAdditionAndMultiplicationParallellyAndNotJoining() {
        startTimer();

        List<CompletableFuture<Integer>> result = completableFutureAddMultiplyList.concatenateAdditionAndMultiplicationParallellyAndNotJoining();
        List<Integer> actualResult = result.stream().map(CompletableFuture::join).collect(Collectors.toList());

        timeTaken(); stopWatchReset();

        assertEquals(CompletableFutureAddMultiplyList.size, actualResult.size());
    }

    @Test
    void concatenateAdditionAndMultiplicationParallellyAndJoining() {
        stopWatchReset();
        startTimer();
        List<Integer> result = completableFutureAddMultiplyList.concatenateAdditionAndMultiplicationParallellyAndJoining();

        timeTaken();stopWatchReset();
        assertEquals(CompletableFutureAddMultiplyList.size, result.size());
    }

    @Test
    void concatenateAdditionAndMultiplicationSequentially() {
        startTimer();
        List<Integer> result = completableFutureAddMultiplyList.concatenateAdditionAndMultiplicationSequentially();
        timeTaken(); stopWatchReset();

        assertEquals(CompletableFutureAddMultiplyList.size, result.size());
    }

     @Test
     void printSomethingAsynchronously() {
        startTimer();
        List<CompletableFuture<Void>> futures = completableFutureAddMultiplyList.printSomethingAsynchronously();
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        timeTaken();
     }
 }