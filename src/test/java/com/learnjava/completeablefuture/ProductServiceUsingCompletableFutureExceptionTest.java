package com.learnjava.completeablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    private ReviewService reviewServicemock = mock(ReviewService.class);

    @Mock
    private ProductInfoService productInfoService = mock(ProductInfoService.class);

    @Mock
    private InventoryService inventoryService = mock(InventoryService.class);
    @InjectMocks
    private ProductServiceUsingCompletableFuture psucfMock;

    @Test
    void retrieveProductDetailsWithInventoryWithCFAndReviewServiceException() {

        when(reviewServicemock.retrieveReviews(any())).thenThrow(new RuntimeException("ReviewException"));
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(inventoryService.addInventory(any())).thenCallRealMethod();

        String productId = "ABC123";
        Product product = psucfMock.retrieveProductDetailsWithInventoryWithCF(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        product.getProductInfo().getProductOptions()
                .forEach( productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
        assertEquals(0,product.getReview().getNoOfReviews());
    }

    @Test
    void retrieveProductDetailsWithInventoryWithCFAndProductInfoServiceException() {

        when(reviewServicemock.retrieveReviews(any())).thenCallRealMethod();
        when(productInfoService.retrieveProductInfo(any()))
                .thenThrow(new RuntimeException("ProductInfoServiceException"));
//        when(inventoryService.addInventory(any())).thenCallRealMethod(); //UnnecessaryStubbingException

        String productId = "ABC123";
        assertThrows(RuntimeException.class, ()->psucfMock.retrieveProductDetailsWithInventoryWithCF(productId));

    }

    @Test
    void retrieveProductDetailsWithInventoryWithCFAndInventoryServiceException() {
        when(reviewServicemock.retrieveReviews(any())).thenCallRealMethod();
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(inventoryService.addInventory(any()))
                .thenThrow(new RuntimeException("Inventory Service Exception"));

        String productId = "ABC123";
        Product product = psucfMock.retrieveProductDetailsWithInventoryWithCF(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        product.getProductInfo().getProductOptions()
                .forEach( productOption -> {
                    assertNotNull(productOption.getInventory());
                    assertEquals(1, productOption.getInventory().getCount());
                });
        assertNotNull(product.getReview());
//        assertEquals(0,product.getReview().getNoOfReviews());
    }
}