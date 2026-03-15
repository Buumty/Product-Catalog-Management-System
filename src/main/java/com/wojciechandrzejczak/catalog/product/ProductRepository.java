package com.wojciechandrzejczak.catalog.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProducer_Name(String name);
}
