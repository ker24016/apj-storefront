package edu.byui.apj.storefront.jms.controller;

import edu.byui.apj.storefront.jms.producer.OrderConfirmationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderConfirmationController {
    @Autowired
    private OrderConfirmationProducer orderConfirmationProducer;

    @GetMapping("/confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable String orderId) {
        try {
            orderConfirmationProducer.sendOrderConfirmation(orderId);
            return ResponseEntity.ok("Order confirm message sent for order ID: " + orderId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send order confirmation message", e);
        }
    }
}
