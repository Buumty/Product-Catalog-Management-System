package com.wojciechandrzejczak.catalog.producer;

import com.wojciechandrzejczak.catalog.product.Product;


public class Producer {
    private String name;

    public Producer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
