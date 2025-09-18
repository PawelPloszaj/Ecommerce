package com.pl.Ecommerce.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long clientId;

    @NotEmpty
    private List<OrderLine> items;

    @Data
    public static class OrderLine {

        @NotNull
        private Long productId;

        @NotNull
        @Min(1)
        private Long quantity;
    }
}
