package edu.byui.apj.storefront.db.controller;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.model.Item;
import edu.byui.apj.storefront.db.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartNoOrder() {
        List<Cart> mockCarts = Collections.singletonList(new Cart());
        when(cartService.getCartsWithoutOrders()).thenReturn(mockCarts);

        ResponseEntity<List<Cart>> response = cartController.getCartNoOrder();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCarts, response.getBody());
    }

    @Test
    void testGetCart() {
        String cartId = "testCartId";
        Cart mockCart = new Cart();
        when(cartService.getCart(cartId)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.getCart(cartId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    void testSaveCart() {
        Cart mockCart = new Cart();
        when(cartService.saveCart(mockCart)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.saveCart(mockCart);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    void testAddItemToCart() {
        String cartId = "testCartId";
        Item mockItem = new Item();
        Cart mockCart = new Cart();
        when(cartService.addItemToCart(cartId, mockItem)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.addItemToCart(cartId, mockItem);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    void testRemoveCart() {
        String cartId = "testCartId";
        doNothing().when(cartService).removeCart(cartId);

        cartController.removeCart(cartId);

        verify(cartService, times(1)).removeCart(cartId);
    }

    @Test
    void testRemoveItemFromCart() {
        String cartId = "testCartId";
        Long itemId = 1L;
        Cart mockCart = new Cart();
        when(cartService.removeItemFromCart(cartId, itemId)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.removeItemFromCart(cartId, itemId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    void testUpdateItemInCart() {
        String cartId = "testCartId";
        Item mockItem = new Item();
        Cart mockCart = new Cart();
        when(cartService.updateCartItem(cartId, mockItem)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.updateItemInCart(cartId, mockItem);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }
}