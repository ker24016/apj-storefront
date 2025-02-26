package edu.byui.apj.storefront.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTests {
    @Autowired
    private TestRestTemplate template;

    @Test
    public void getHealth() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/health", String.class);
        assertThat(response.getBody()).isEqualTo("200 OK");
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

}
