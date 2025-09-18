package com.pl.Ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.Ecommerce.requests.CreateClientRequest;
import com.pl.Ecommerce.requests.CreateOrderRequest;
import com.pl.Ecommerce.requests.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldCreateOrderAndGetByOrderNumber() throws Exception {

        var clientRequest = new CreateClientRequest();
        clientRequest.setName("Pawel");
        clientRequest.setSurname("Test");
        clientRequest.setEmail("pawel@example.com");
        clientRequest.setPhoneNumber("123456789");
        clientRequest.setStreet("Testowa");
        clientRequest.setHouseNumber("1");
        clientRequest.setCity("Bydgoszcz");
        clientRequest.setCountry("PL");
        clientRequest.setPostalCode("85-567");

        String clientJson = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var createdClient = objectMapper.readTree(clientJson);
        long clientId = createdClient.get("id").asLong();

        var productRequest = new CreateProductRequest();
        productRequest.setName("Laptop");
        productRequest.setPriceNet(new BigDecimal("2000"));
        productRequest.setVat(new BigDecimal("0.23"));
        productRequest.setQuantity(5L);
        productRequest.setIsActive(true);

        String productJson = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var createdProduct = objectMapper.readTree(productJson);
        long productId = createdProduct.get("id").asLong();

        var line = new CreateOrderRequest.OrderLine();
        line.setProductId(productId);
        line.setQuantity(2L);

        var orderRequest = new CreateOrderRequest();
        orderRequest.setClientId(clientId);
        orderRequest.setItems(List.of(line));

        String orderJson = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String orderNumber = objectMapper.readTree(orderJson).get("orderNumber").asText();

        mockMvc.perform(get("/api/orders/" + orderNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client.id").value(clientId))
                .andExpect(jsonPath("$.items[0].productId").value(productId))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }
}
