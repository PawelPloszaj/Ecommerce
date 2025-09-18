package com.pl.Ecommerce.unit;

import com.pl.Ecommerce.domain.Client;
import com.pl.Ecommerce.domain.Order;
import com.pl.Ecommerce.domain.Product;
import com.pl.Ecommerce.repository.ClientRepository;
import com.pl.Ecommerce.repository.OrderRepository;
import com.pl.Ecommerce.repository.ProductRepository;
import com.pl.Ecommerce.requests.CreateOrderRequest;
import com.pl.Ecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        clientRepository = mock(ClientRepository.class);
        productRepository = mock(ProductRepository.class);
        orderService = new OrderService(orderRepository, clientRepository, productRepository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        Client client = new Client();
        client.setClientId(1L);

        Product product = new Product();
        product.setProductId(10L);
        product.setName("Kubek");
        product.setPriceNet(new BigDecimal("10.00"));
        product.setVat(new BigDecimal("0.23"));
        product.setQuantity(5L);
        product.setIsActive(true);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        CreateOrderRequest.OrderLine line = new CreateOrderRequest.OrderLine();
        line.setProductId(10L);
        line.setQuantity(2L);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setClientId(1L);
        request.setItems(List.of(line));

        Order order = orderService.createOrder(request);

        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getTotalPriceNet()).isEqualByComparingTo("20.00");
        assertThat(order.getTotalPriceGross()).isEqualByComparingTo("24.60");

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue().getQuantity()).isEqualTo(3L);
    }

    @Test
    void shouldThrowWhenProductNotActive() {
        Client client = new Client();
        client.setClientId(1L);

        Product product = new Product();
        product.setProductId(10L);
        product.setPriceNet(new BigDecimal("10.00"));
        product.setVat(new BigDecimal("0.23"));
        product.setQuantity(5L);
        product.setIsActive(false);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        CreateOrderRequest.OrderLine line = new CreateOrderRequest.OrderLine();
        line.setProductId(10L);
        line.setQuantity(1L);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setClientId(1L);
        request.setItems(List.of(line));

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not active");
    }

    @Test
    void createOrder_shouldThrowWhenClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        CreateOrderRequest request = new CreateOrderRequest();
        request.setClientId(1L);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(request));
    }
}
