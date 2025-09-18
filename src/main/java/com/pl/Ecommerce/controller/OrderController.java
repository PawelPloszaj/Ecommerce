package com.pl.Ecommerce.controller;

import com.pl.Ecommerce.service.OrderService;
import com.pl.Ecommerce.domain.Order;
import com.pl.Ecommerce.dto.ClientDto;
import com.pl.Ecommerce.dto.ItemDto;
import com.pl.Ecommerce.dto.OrderDto;
import com.pl.Ecommerce.requests.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        OrderDto orderDto = mapToDto(order);
        return ResponseEntity.status(201).body(orderDto);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDto> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        OrderDto orderDto = mapToDto(order);
        return ResponseEntity.ok(orderDto);
    }

    private OrderDto mapToDto(Order order) {
        ClientDto clientDto = new ClientDto(
                order.getClient().getClientId(),
                order.getClient().getName(),
                order.getClient().getSurname(),
                order.getClient().getEmail(),
                order.getClient().getPhoneNumber(),
                order.getClient().getStreet(),
                order.getClient().getHouseNumber(),
                order.getClient().getApartmentNumber(),
                order.getClient().getCity(),
                order.getClient().getCountry(),
                order.getClient().getPostalCode()
        );

        var items = order.getItems().stream().map(item -> new ItemDto(
                item.getItemId(),
                item.getProduct().getProductId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPriceNet(),
                item.getPriceGross()
        )).collect(Collectors.toList());

        return new OrderDto(
                order.getOrderId(),
                order.getOrderNumber(),
                clientDto,
                items,
                order.getTotalPriceNet(),
                order.getTotalPriceGross(),
                order.getOrderDate(),
                order.getStatus()
        );
    }
}
