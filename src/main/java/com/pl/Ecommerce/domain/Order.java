package com.pl.Ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderId;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @NotNull
    private BigDecimal totalPriceNet;

    @NotNull
    private BigDecimal totalPriceGross;

    private LocalDateTime orderDate = LocalDateTime.now();

    private String status = "Pending";
}
