package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteableFutureHelloWorldExceptionTest {

    @Mock
    private HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    private CompleteableFutureHelloWorldException completeableFutureHelloWorldException;
    @Test
    void helloWorldCombineWith3AsyncCallsAndHandleMethod() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completeableFutureHelloWorldException.helloWorldCombineWith3AsyncCallsAndHandleMethod();

        assertNotNull(result);
        assertEquals(" WORLD!GENERAL KENOBI!", result);
    }

    @Test
    void helloWorldCombineWith3AsyncCallsAndHandleMethodAndTwoException() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        String result = completeableFutureHelloWorldException.helloWorldCombineWith3AsyncCallsAndHandleMethod();

        assertNotNull(result);
        assertEquals("GENERAL KENOBI!", result);
    }

    @Test
    void helloWorldCombineWith3AsyncCallsAndExceptionallyMethod() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completeableFutureHelloWorldException.helloWorldCombineWith3AsyncCallsAndExceptionallyMethod();

        assertNotNull(result);
        assertEquals(" WORLD!GENERAL KENOBI!", result);
    }

    @Test
    void helloWorldCombineWith3AsyncCallsAndExceptionallyMethodAndTwoException() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        String result = completeableFutureHelloWorldException.helloWorldCombineWith3AsyncCallsAndExceptionallyMethod();

        assertNotNull(result);
        assertEquals("GENERAL KENOBI!", result);
    }

    //throws completionException since, the original exception isn't handled by thenComplete()
    @Test
    void helloWorldCombineWith3AsyncCallsAndWhenCompleteMethod() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completeableFutureHelloWorldException.helloWorldCombineWith3AsyncCallsAndWhenCompleteMethod();

        assertNotNull(result);
        assertEquals(" WORLD!GENERAL KENOBI!", result);
    }

}