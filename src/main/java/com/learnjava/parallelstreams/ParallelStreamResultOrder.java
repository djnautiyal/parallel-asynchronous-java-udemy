package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {
    public static List<Integer> listOrder(List<Integer> inputList){
        return inputList.parallelStream()
                .map( x -> x*2 )
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputSet){
        return inputSet.parallelStream()
                .map( x -> x*2 )
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        startTimer();
        List<Integer> integerList = List.of(1,2,3,4,5,6,7,8,9);
        log("inputList: " + integerList);
        List<Integer> resultList = listOrder(integerList);
        log("resultList: " + resultList);
        timeTaken();
        stopWatchReset();

        startTimer();
        Set<Integer> integerSet = Set.of(1,2,3,4,5,6,7,8,9);
        log("integerSet: " + integerSet);
        Set<Integer> resultSet = setOrder(integerSet);
        log("resultSet: " + resultSet);
        timeTaken();
    }

}
