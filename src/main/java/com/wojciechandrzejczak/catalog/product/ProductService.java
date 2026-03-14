package com.wojciechandrzejczak.catalog.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getProducer().getName().isBlank()) {
            throw new IllegalArgumentException("Producer name cannot be blank");
        }
        if (product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (product.getDescription().isBlank()) {
            throw new IllegalArgumentException("Product description name cannot be blank");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be lower than 0.00");
        }
        return productRepository.save(product);
    }
}
