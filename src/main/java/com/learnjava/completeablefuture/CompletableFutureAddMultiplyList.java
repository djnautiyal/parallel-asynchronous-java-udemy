package com.learnjava.completeablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.delay;

public class CompletableFutureAddMultiplyList {

    static final int size = 20;
    public BinaryOperator<Integer> addition = (x,y) -> {
        delay(500);
        return x+y;
    };
    public BinaryOperator<Integer> multiplication = (x,y) -> {
        delay(500);
        return x*y;
    };

    public List<CompletableFuture<Integer>> concatenateAdditionAndMultiplicationParallellyAndNotJoining(){
        return Stream.iterate(new int[]{0,1,2}, x -> new int[]{x[0], x[0]+x[1], x[1]+x[2]})
                .limit(CompletableFutureAddMultiplyList.size)
                .map(x -> CompletableFuture.supplyAsync(() -> addition.apply(x[0], x[1]))
                        .thenApply(y -> multiplication.apply(y, x[2])))
                .collect(Collectors.toList());
    }

    public List<Integer> concatenateAdditionAndMultiplicationParallellyAndJoining(){
        List<CompletableFuture<Integer>> result = new ArrayList<>();
        result = Stream.iterate(new int[]{0,1,2}, x -> new int[]{x[0], x[0]+x[1], x[1]+x[2]})
                .limit(CompletableFutureAddMultiplyList.size)
                .map(x -> CompletableFuture.supplyAsync(() -> addition.apply(x[0], x[1]))
                        .thenApply(y -> multiplication.apply(y, x[2])))
                .collect(Collectors.toList());

        return result.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<Integer> concatenateAdditionAndMultiplicationSequentially(){
        return Stream.iterate(new int[]{0,1,2}, x -> new int[]{x[0], x[0]+x[1], x[1]+x[2]})
                .limit(CompletableFutureAddMultiplyList.size)
                .map(x -> multiplication.apply(addition.apply(x[0],x[1]), x[2]))
                .collect(Collectors.toList());
    }

    public List<CompletableFuture<Void>> printSomethingAsynchronously(){

        return Stream.iterate(new int[]{0,1,2}, x -> new int[]{x[0], x[0]+x[1], x[1]+x[2]})
                .limit(CompletableFutureAddMultiplyList.size)
                .map(x -> CompletableFuture
                        .runAsync(() -> {
                            delay(500);
                            System.out.println(x[0] + "\t" + x[1] + "\t" + x[2]);
                        }))
                .collect(Collectors.toList());
    }
}
