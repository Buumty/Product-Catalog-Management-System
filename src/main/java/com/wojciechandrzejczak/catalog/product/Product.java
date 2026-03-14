package com.wojciechandrzejczak.catalog.product;

import com.wojciechandrzejczak.catalog.producer.Producer;

import java.math.BigDecimal;
import java.util.Map;

public class Product {
    private Producer producer;
    private String name;
    private String description;
    private BigDecimal price;
    private Map<String, Object> otherAttributesMap;

    public Product(Producer producer, String name, String description, BigDecimal price, Map<String, Object> otherAttributesMap) {
        this.producer = producer;
        this.name = name;
        this.description = description;
        this.price = price;
        this.otherAttributesMap = otherAttributesMap;
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

    public Map<String, Object> getOtherAttributesMap() {
        return otherAttributesMap;
    }

    public void setOtherAttributesMap(Map<String, Object> otherAttributesMap) {
        this.otherAttributesMap = otherAttributesMap;
    }
}
