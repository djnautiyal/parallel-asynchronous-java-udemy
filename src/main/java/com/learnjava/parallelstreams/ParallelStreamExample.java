package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamExample {

    public List<String> stringTransform(List<String> namesList){
        return namesList
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransformBoolean(List<String> namesList, boolean isParallel){
        Stream<String> namesStream = namesList.stream();

        if(isParallel)
            namesStream.parallel();

        return namesStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());

    }
    public static void main(String[] args) {

        stopWatch.start();

        List<String> names = DataSet.namesList();

        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        List<String> resultList = parallelStreamExample.stringTransform(names);

        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
