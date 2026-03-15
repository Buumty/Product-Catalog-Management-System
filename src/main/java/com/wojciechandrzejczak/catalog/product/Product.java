package com.wojciechandrzejczak.catalog.product;

import com.wojciechandrzejczak.catalog.producer.Producer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;
    private String name;
    private String description;
    private BigDecimal price;
    @ElementCollection
    @CollectionTable(name = "product_attributes", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    private Map<String, String> otherAttributesMap;

    public Product() {
    }

    public Product(Producer producer, String name, String description, BigDecimal price, Map<String, String> otherAttributesMap) {
        this.producer = producer;
        this.name = name;
        this.description = description;
        this.price = price;
        this.otherAttributesMap = otherAttributesMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<String, String> getOtherAttributesMap() {
        return otherAttributesMap;
    }

    public void setOtherAttributesMap(Map<String, String> otherAttributesMap) {
        this.otherAttributesMap = otherAttributesMap;
    }
}
