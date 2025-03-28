package edu.byui.apj.storefront.db.controller;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        CardOrder mockOrder = new CardOrder();
        when(orderService.saveOrder(mockOrder)).thenReturn(mockOrder);

        ResponseEntity<CardOrder> response = orderController.saveOrder(mockOrder);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    void testGetOrder() {
        Long orderId = 1L;
        CardOrder mockOrder = new CardOrder();
        when(orderService.getOrder(orderId)).thenReturn(Optional.of(mockOrder));

        ResponseEntity<CardOrder> response = orderController.getOrder(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    void testGetOrderNotFound() {
        Long orderId = 1L;
        when(orderService.getOrder(orderId)).thenReturn(Optional.empty());

        ResponseEntity<CardOrder> response = orderController.getOrder(orderId);

        assertEquals(404, response.getStatusCodeValue());
    }
}