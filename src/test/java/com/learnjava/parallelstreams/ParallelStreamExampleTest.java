package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamExampleTest {

    ParallelStreamExample parallelStreamExample  = new ParallelStreamExample();
    @Test
    void stringTransform() {
        List<String> namesList = DataSet.namesList();

        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(namesList);
        timeTaken();

        assertEquals(namesList.size(), resultList.size());
        resultList.forEach(name ->{
            assertTrue(name.contains("-"));
        });
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void stringTransformBoolean(boolean isParallel) {
        List<String> namesList = DataSet.namesList();

        stopWatchReset();
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransformBoolean(namesList, isParallel);
        timeTaken();

        assertEquals(namesList.size(), resultList.size());
        resultList.forEach(name ->{
            assertTrue(name.contains("-"));
        });

    }
}