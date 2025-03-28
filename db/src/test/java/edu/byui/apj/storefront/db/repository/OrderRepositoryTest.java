package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.CardOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder() {
        CardOrder mockOrder = new CardOrder();
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        CardOrder savedOrder = orderRepository.save(mockOrder);

        assertEquals(mockOrder, savedOrder);
        verify(orderRepository, times(1)).save(mockOrder);
    }

    @Test
    void testFindById() {
        Long orderId = 1L;
        CardOrder mockOrder = new CardOrder();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Optional<CardOrder> foundOrder = orderRepository.findById(orderId);

        assertEquals(Optional.of(mockOrder), foundOrder);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFindByIdNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<CardOrder> foundOrder = orderRepository.findById(orderId);

        assertEquals(Optional.empty(), foundOrder);
        verify(orderRepository, times(1)).findById(orderId);
    }
}