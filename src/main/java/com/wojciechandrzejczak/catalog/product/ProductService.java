package com.wojciechandrzejczak.catalog.product;

import com.wojciechandrzejczak.catalog.producer.Producer;
import com.wojciechandrzejczak.catalog.producer.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProducerRepository producerRepository) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
    }


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findAllProductsByProducerName(String producerName) {
        if (producerName == null || producerName.isBlank()) {
            throw new IllegalArgumentException("Producer name cannot be null or blank");
        }

        return productRepository.findByProducer_Name(producerName);
    }

    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        Producer producer = product.getProducer();

        if (producer == null || product.getProducer().getName().isBlank()) {
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

        Producer savedProducer = producerRepository.findByName(producer.getName())
                .orElseGet(() -> producerRepository.save(new Producer(producer.getName())));

        product.setProducer(savedProducer);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product newProduct) {

        Product existingProduct = productRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!newProduct.getProducer().getName().isBlank()) {
            existingProduct.getProducer().setName(newProduct.getProducer().getName());
        } else throw new IllegalArgumentException("Producer name cannot be blank");

        if (!newProduct.getName().isBlank()) {
            existingProduct.setName(newProduct.getName());
        } else throw new IllegalArgumentException("Product name cannot be blank");

        if (!newProduct.getDescription().isBlank()) {
            existingProduct.setDescription(newProduct.getDescription());
        } else throw new IllegalArgumentException("Product description cannot be blank");

        if (newProduct.getPrice() != null) {
            if (newProduct.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Product price cannot be lower than 0.00");
            existingProduct.setPrice(newProduct.getPrice());
        } else throw new IllegalArgumentException("Product price cannot be null");

        if (newProduct.getOtherAttributesMap() != null) {
            existingProduct.setOtherAttributesMap(newProduct.getOtherAttributesMap());
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);

        productRepository.delete(product);
    }


}
