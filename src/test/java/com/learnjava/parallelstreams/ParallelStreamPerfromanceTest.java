package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamPerfromanceTest {
    ParallelStreamPerfromance parallelStreamPerfromance = new ParallelStreamPerfromance();
    final int count = 1_000_000;
    @Test
    void sumUsingIntStream() {
        int sum = parallelStreamPerfromance.sumUsingIntStream(count,false);
        System.out.println("sumUsingIntStream: " + sum);

        assertEquals(1784293664, sum);
    }
    @Test
    void sumUsingIntStreamParallel() {
        int sum = parallelStreamPerfromance.sumUsingIntStream(count,true);
        System.out.println("sumUsingIntStreamParallel: " + sum);

        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingList() {
        List<Integer> integerList = DataSet.generateIntegerList(count);
        int sum = parallelStreamPerfromance.sumUsingList(integerList, false);
        System.out.println("sumUsingList: " + sum);

        assertEquals(1784293664, sum);
    }
    @Test
    void sumUsingListParallel() {
        List<Integer> integerList = DataSet.generateIntegerList(count);
        int sum = parallelStreamPerfromance.sumUsingList(integerList, true);
        System.out.println("sumUsingListParallel: " + sum);

        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingIterate() {
        int sum = parallelStreamPerfromance.sumUsingIterate(count, false);
        System.out.println("sumUsingIterate: " + sum);

        assertEquals(1784293664, sum);
    }
    @Test
    void sumUsingIterateParallel() {
        int sum = parallelStreamPerfromance.sumUsingIterate(count, true);
        System.out.println("sumUsingIterateParallel: " + sum);

        assertEquals(1784293664, sum);
    }
}