package com.pl.Ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ItemId;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @Min(1)
    private Long quantity;

    @NotNull
    private BigDecimal priceNet;

    @NotNull
    private BigDecimal priceGross;
}
