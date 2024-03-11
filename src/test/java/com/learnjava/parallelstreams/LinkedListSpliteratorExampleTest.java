package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();
    @RepeatedTest(5)
    void multiplyEachValueSequential() {
        int size = 1_000_000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> returnList = linkedListSpliteratorExample
                .multiplyEachValue(inputList, 3 , false);

        assertEquals(size, returnList.size());
    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        int size = 1_000_000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        List<Integer> returnList = linkedListSpliteratorExample
                .multiplyEachValue(inputList, 3 , true);

        assertEquals(size, returnList.size());
    }
}