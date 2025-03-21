package edu.byui.apj.storefront.jms.consumer;

import edu.byui.apj.storefront.model.CardOrder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class OrderConfirmationConsumer {

    @JmsListener(destination = "orderQueue")
    public void receiveOrderConfirmation(String orderId) {
        try {
            log.info("Received order confirmation for order ID: {}", orderId);
            WebClient webClient = WebClient.builder().build();
            CardOrder order = webClient.get().uri("http://localhost:8083/order/" + orderId).retrieve().bodyToMono(CardOrder.class).block();
            log.info("Order confirmation: {}", order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}