package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ParallelStreamPerfromance {

    public int sumUsingIntStream(int count, boolean isParallel) {
        startTimer();
        IntStream integerStream = IntStream
                .rangeClosed(0, count);

        if (isParallel) integerStream.parallel();

        int sum = integerStream.sum();
        timeTaken();
        stopWatchReset();
        return sum;
    }

    public int sumUsingList(List<Integer> inputList, boolean isParallel) {

        startTimer();

        Stream<Integer> inputStream = inputList.stream();

        if (isParallel) inputStream.parallel();

        int sum = inputStream
                .mapToInt(Integer::intValue)
                .sum();

        timeTaken();
        stopWatchReset();
        return sum;
    }

    public int sumUsingIterate(int n, boolean isParallel) {
        startTimer();
        Stream<Integer> integerStream = Stream
                .iterate(0, i -> i + 1);

        if (isParallel) integerStream.parallel();

        int sum = integerStream
                .limit(n + 1)
                .reduce(0, Integer::sum);

        timeTaken();
        stopWatchReset();
        return sum;
    }
}
