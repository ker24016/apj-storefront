package edu.byui.apj.storefront.db.service;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        CardOrder mockOrder = new CardOrder();
        Cart mockCart = new Cart();
        mockCart.setId("cart1");
        mockOrder.setCart(mockCart);

        when(cartService.getCart("cart1")).thenReturn(mockCart);
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        CardOrder savedOrder = orderService.saveOrder(mockOrder);

        assertEquals(mockOrder, savedOrder);
        verify(cartService, times(1)).getCart("cart1");
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testGetOrder() {
        Long orderId = 1L;
        CardOrder mockOrder = new CardOrder();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Optional<CardOrder> foundOrder = orderService.getOrder(orderId);

        assertTrue(foundOrder.isPresent());
        assertEquals(mockOrder, foundOrder.get());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<CardOrder> foundOrder = orderService.getOrder(orderId);

        assertTrue(foundOrder.isEmpty());
        verify(orderRepository, times(1)).findById(orderId);
    }
}