package com.learnjava.completeablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceUsingCompletableFutureTest {
    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    InventoryService inventoryService = new InventoryService();
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture
            = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);
    ProductService productService = new ProductService(productInfoService, reviewService);

    @Test
    void retrieveProductDetailsUsingCompletableFuture() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails() {
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithoutJoinButAssertsInThenApply() {
        String productId = "ABC123";
        startTimer();
        CompletableFuture<Product> productCompletableFuture = productServiceUsingCompletableFuture.retrieveProductDetailsWithoutJoin(productId);

        Product product = productCompletableFuture.join();

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());

        timeTaken(); stopWatchReset();
    }

    @Test
    void retrieveProductDetailsWithoutJoinButAssertsNotInThenApply() {
        String productId = "ABC123";
        startTimer();
        CompletableFuture<Product> productCompletableFuture = productServiceUsingCompletableFuture.retrieveProductDetailsWithoutJoin(productId);

        productCompletableFuture
                .thenAccept(product -> {
                    assertNotNull(product);
                    assertFalse(product.getProductInfo().getProductOptions().isEmpty());
                    assertNotNull(product.getReview());
                }).join();

        timeTaken(); stopWatchReset();
    }


    @Test
    void retrieveProductDetailsWithInventory() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        product.getProductInfo().getProductOptions()
                .forEach( productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventoryWithCF() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryWithCF(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        product.getProductInfo().getProductOptions()
                .forEach( productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }
}