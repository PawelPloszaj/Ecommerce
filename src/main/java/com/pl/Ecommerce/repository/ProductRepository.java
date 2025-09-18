package com.pl.Ecommerce.repository;

import com.pl.Ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
