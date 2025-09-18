package com.pl.Ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.Ecommerce.requests.CreateClientRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ClientControllerTest {

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
    void shouldRegisterClient() throws Exception {

        CreateClientRequest request = new CreateClientRequest();
        request.setName("Pawel");
        request.setSurname("Test");
        request.setEmail("pawel@example.com");
        request.setPhoneNumber("123456789");
        request.setStreet("Testowa");
        request.setHouseNumber("1");
        request.setCity("Bydgoszcz");
        request.setCountry("PL");
        request.setPostalCode("85-567");

        String responseJson = mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pawel"))
                .andExpect(jsonPath("$.email").value("pawel@example.com"))
                .andReturn().getResponse().getContentAsString();

        var createdClient = objectMapper.readTree(responseJson);
        long clientId = createdClient.get("id").asLong();

        assert clientId > 0;

    }
}
