package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {
    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);
    @ParameterizedTest
    @ValueSource(ints = {13})
    void checkout(int count) {
        Cart cart = DataSet.createCart(count);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        if(count <=6) assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.SUCCESS);
        else assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.FAILURE);
    }

    @Test
    void checkout6items() {
        Cart cart = DataSet.createCart(6);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.SUCCESS);
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void modifyParallelism() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
        Cart cart = DataSet.createCart(100);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(checkoutResponse.getCheckoutStatus(), CheckoutStatus.FAILURE);
    }

    @Test
    public void noOfCores(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void paralellism(){
        System.out.println("parallelism: " + ForkJoinPool.getCommonPoolParallelism());
    }
}