package edu.byui.apj.storefront.jms;

import edu.byui.apj.storefront.model.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CartCleanupJob {

    public static final WebClient WEB_CLIENT = WebClient.builder().baseUrl("http://localhost:8083").build();

    public void cleanupCart(String cartId) {
        log.debug("Cleaning up cart with ID: {}", cartId);
        try {
            WEB_CLIENT.delete().uri("/cart/" + cartId).retrieve().toBodilessEntity().block();
            log.info("Successfully cleaned up cart with ID: {}", cartId);
        } catch (Exception e) {
            log.error("Failed to clean up cart with ID: {}. HTTP status code: {}", cartId, e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupCarts() {
        log.debug("Starting scheduled cart cleanup");
        List<Cart> cartsToClean = getCartsWithoutOrders();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (Cart cart : cartsToClean) {
            executorService.submit(() -> cleanupCart(cart.getId()));
        }
        executorService.close();
        log.info("Cart cleanup complete");
    }

    public List<Cart> getCartsWithoutOrders() {
        log.debug("Getting carts without orders");
        try {
            return WEB_CLIENT.get().uri("/cart/noorder").retrieve().bodyToFlux(Cart.class).collectList().block();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
