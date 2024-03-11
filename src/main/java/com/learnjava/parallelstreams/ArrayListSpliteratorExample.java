package com.learnjava.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ArrayListSpliteratorExample{

    List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue, boolean isParallel){
        Stream<Integer> integerStream = inputList.stream();

        if(isParallel)
            integerStream.parallel();

        startTimer();
        List<Integer> returnList =  integerStream
                .map(x -> x*multiplyValue)
                .collect(Collectors.toList());

        timeTaken();
        stopWatchReset();
        return returnList;
    }
}
