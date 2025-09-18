package com.pl.Ecommerce.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductId;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal priceNet;

    @NotNull
    private BigDecimal vat;

    @NotNull
    @Min(0)
    private Long quantity;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private Boolean isActive = true;

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
