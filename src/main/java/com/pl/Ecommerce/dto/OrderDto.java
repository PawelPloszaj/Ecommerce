package com.pl.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String orderNumber;
    private ClientDto client;
    private List<ItemDto> items;
    private BigDecimal totalPriceNet;
    private BigDecimal totalPriceGross;
    private LocalDateTime orderDate;
    private String status;
}
