package com.pl.Ecommerce.controller;

import com.pl.Ecommerce.service.ProductService;
import com.pl.Ecommerce.domain.Product;
import com.pl.Ecommerce.dto.ProductDto;
import com.pl.Ecommerce.requests.CreateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        ProductDto productDto = new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPriceNet(),
                product.getVat(),
                product.getQuantity(),
                product.getIsActive()
        );
        return ResponseEntity.status(201).body(productDto);
    }
}
