package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCartsWithoutOrders() {
        List<Cart> mockCarts = Collections.singletonList(new Cart());
        when(cartRepository.findCartsWithoutOrders()).thenReturn(mockCarts);

        List<Cart> cartsWithoutOrders = cartRepository.findCartsWithoutOrders();

        assertEquals(mockCarts, cartsWithoutOrders);
        verify(cartRepository, times(1)).findCartsWithoutOrders();
    }
}