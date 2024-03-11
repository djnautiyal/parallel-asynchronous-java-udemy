package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {
    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValueSequentially() {
        int size = 1_000_000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        List<Integer> returnList =  arrayListSpliteratorExample
                .multiplyEachValue(inputList, 2, false);

        assertEquals(size, returnList.size());

    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        int size = 1_000_000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        List<Integer> returnList =  arrayListSpliteratorExample
                .multiplyEachValue(inputList, 2, true);

        assertEquals(size, returnList.size());

    }
}