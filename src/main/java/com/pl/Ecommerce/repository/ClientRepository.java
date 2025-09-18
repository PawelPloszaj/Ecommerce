package com.pl.Ecommerce.repository;

import com.pl.Ecommerce.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
