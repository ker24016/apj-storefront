package edu.byui.apj.storefront.db.service;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.model.Item;
import edu.byui.apj.storefront.db.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemToCart() {
        String cartId = "cart1";
        Item item = new Item();
        item.setId(1L);
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.addItemToCart(cartId, item);

        assertEquals(1, updatedCart.getItems().size());
        assertEquals(item, updatedCart.getItems().get(0));
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveItemFromCart() {
        String cartId = "cart1";
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        Cart cart = new Cart();
        cart.setId(cartId);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.removeItemFromCart(cartId, itemId);

        assertEquals(0, updatedCart.getItems().size());
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testUpdateCartItem() {
        String cartId = "cart1";
        Item item = new Item();
        item.setId(1L);
        Cart cart = new Cart();
        cart.setId(cartId);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Item");

        Cart updatedCart = cartService.updateCartItem(cartId, updatedItem);

        assertEquals(1, updatedCart.getItems().size());
        assertEquals(updatedItem, updatedCart.getItems().get(0));
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testGetCart() {
        String cartId = "cart1";
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.getCart(cartId);

        assertEquals(cart, foundCart);
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void testGetCartNotFound() {
        String cartId = "cart1";

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.getCart(cartId));
        verify(cartRepository, times(1)).findById(cartId);
    }
}