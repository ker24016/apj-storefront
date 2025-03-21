package edu.byui.apj.storefront.jms.producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConfirmationProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendOrderConfirmation(String orderId) {
        jmsTemplate.convertAndSend("orderQueue", orderId);
        log.info("Order confirmation message sent for order ID: {}", orderId);
    }
}