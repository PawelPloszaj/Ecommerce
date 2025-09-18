package com.pl.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal priceNet;
    private BigDecimal vat;
    private Long quantity;
    private Boolean isActive;
}
