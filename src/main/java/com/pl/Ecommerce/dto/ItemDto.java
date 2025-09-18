package com.pl.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal priceNet;
    private BigDecimal priceGross;
}
