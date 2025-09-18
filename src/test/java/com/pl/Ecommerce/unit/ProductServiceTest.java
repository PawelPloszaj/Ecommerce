package com.pl.Ecommerce.unit;

import com.pl.Ecommerce.domain.Product;
import com.pl.Ecommerce.repository.ProductRepository;
import com.pl.Ecommerce.requests.CreateProductRequest;
import com.pl.Ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void createProduct_shouldSaveProduct() {
        CreateProductRequest req = new CreateProductRequest();
        req.setName("Laptop");
        req.setPriceNet(BigDecimal.valueOf(2000));
        req.setVat(BigDecimal.valueOf(0.23));
        req.setQuantity(5L);

        Product saved = new Product();
        saved.setProductId(10L);
        saved.setName("Laptop");

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product product = productService.createProduct(req);

        assertThat(product.getProductId()).isEqualTo(10L);
        assertThat(product.getName()).isEqualTo("Laptop");

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnProduct() {
        Product product = new Product();
        product.setProductId(5L);

        when(productRepository.findById(5L)).thenReturn(Optional.of(product));

        Product found = productService.getProductById(5L);

        assertThat(found).isNotNull();
        assertThat(found.getProductId()).isEqualTo(5L);
    }

    @Test
    void getProduct_shouldThrowWhenNotFound() {
        when(productRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(42L));
    }
}
