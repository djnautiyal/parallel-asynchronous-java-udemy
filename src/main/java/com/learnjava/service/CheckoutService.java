package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.stream.Collectors.summingDouble;

public class CheckoutService {
    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService){
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){

        startTimer();
        List<CartItem> expiredItemList = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    boolean isCartItemInvalid =priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isCartItemInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();
        stopWatchReset();
        if(!expiredItemList.isEmpty()) return new CheckoutResponse(CheckoutStatus.FAILURE, expiredItemList);

        double finalPrice = CalculateFinalPrice(cart);
        double finalPriceByReduce = CalculateFinalPriceByReduce(cart);
        log("Final Price is:" + finalPriceByReduce);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice  );
    }

    private double CalculateFinalPrice(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue).sum();
    }
    private double CalculateFinalPriceByReduce(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, (x,y) -> x+y );
    }
}
