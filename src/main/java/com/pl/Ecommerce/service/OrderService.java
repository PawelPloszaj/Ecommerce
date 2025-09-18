package com.pl.Ecommerce.service;

import com.pl.Ecommerce.repository.ProductRepository;
import com.pl.Ecommerce.domain.Client;
import com.pl.Ecommerce.domain.Item;
import com.pl.Ecommerce.domain.Order;
import com.pl.Ecommerce.domain.Product;
import com.pl.Ecommerce.repository.ClientRepository;
import com.pl.Ecommerce.repository.OrderRepository;
import com.pl.Ecommerce.requests.CreateOrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setOrderNumber(generateOrderNumber());

        BigDecimal totalNet = BigDecimal.ZERO;
        BigDecimal totalGross = BigDecimal.ZERO;

        for (CreateOrderRequest.OrderLine line : request.getItems()) {
            Product product = productRepository.findById(line.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (!product.getIsActive()) {
                throw new RuntimeException("Product is not active: " + product.getName());
            }

            if (product.getQuantity() < line.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal priceNet = product.getPriceNet().multiply(BigDecimal.valueOf(line.getQuantity()));
            BigDecimal priceGross = priceNet.multiply(BigDecimal.ONE.add(product.getVat()));

            Item orderItem = new Item();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(line.getQuantity());
            orderItem.setPriceNet(priceNet);
            orderItem.setPriceGross(priceGross);
            order.getItems().add(orderItem);

            product.setQuantity(product.getQuantity() - line.getQuantity());
            totalNet = totalNet.add(priceNet);
            totalGross = totalGross.add(priceGross);
            productRepository.save(product);
        }

        order.setTotalPriceNet(totalNet);
        order.setTotalPriceGross(totalGross);

        return orderRepository.save(order);
    }

    @Transactional
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private String randomSuffix() {
        int randomNum = (int) (Math.random() * 10000);
        return String.format("%04d", randomNum);
    }

    private String generateOrderNumber() {
        return "ORDER-" + randomSuffix();
    }
}
