package com.pl.Ecommerce.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal priceNet;

    @NotNull
    private BigDecimal vat;

    @NotNull
    @Min(0)
    private Long quantity;

    @NotNull
    private Boolean isActive;
}
