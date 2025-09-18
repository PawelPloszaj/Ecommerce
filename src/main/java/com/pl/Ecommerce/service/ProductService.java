package com.pl.Ecommerce.service;

import com.pl.Ecommerce.domain.Product;
import com.pl.Ecommerce.repository.ProductRepository;
import com.pl.Ecommerce.requests.CreateProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPriceNet(request.getPriceNet());
        product.setVat(request.getVat());
        product.setQuantity(request.getQuantity());
        product.setIsActive(request.getIsActive());
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
