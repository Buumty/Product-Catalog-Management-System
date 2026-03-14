package com.wojciechandrzejczak.catalog.product;

import com.wojciechandrzejczak.catalog.producer.Producer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void givenProductsInDatabase_whenFindAllProducts_thenReturnAllProducts() {
        Product p1Test = new Product(
                new Producer("Samsung"),
                "SAMSUNG QE98QN990F 98",
                "Samsung TV",
                new BigDecimal("4999.99"),
                new HashMap<>());
        Product p2Test = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5",
                "Samsung Smartphone",
                new BigDecimal("1499.99"),
                new HashMap<>());
        Product p3Test = new Product(
                new Producer("Apple"),
                "Smartphone Iphone 13",
                "Apple Smartphone",
                new BigDecimal("1999.99"),
                new HashMap<>());

        List<Product> productList = new ArrayList<>();
        productList.add(p1Test);
        productList.add(p2Test);
        productList.add(p3Test);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> allProducts = productService.findAllProducts();

        assertEquals(productList.size(), allProducts.size());
        verify(productRepository).findAll();
    }

    @Test
    void givenNoProductsInDatabase_whenFindAllProducts_thenReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> allProducts = productService.findAllProducts();

        assertEquals(0, allProducts.size());
        verify(productRepository).findAll();
    }

    @Test
    void givenProductInDatabase_whenFindAllProducts_thenReturnCorrectProductWithCorrectDetails() {
        Product p1Test = new Product(
                new Producer("Samsung"),
                "SAMSUNG QE98QN990F 98",
                "Samsung TV",
                new BigDecimal("4999.99"),
                new HashMap<>());

        List<Product> productList = new ArrayList<>();
        productList.add(p1Test);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> allProducts = productService.findAllProducts();
        Product productFromDb = allProducts.get(0);

        assertEquals(p1Test.getProducer().getName(), productFromDb.getProducer().getName());
        assertEquals(p1Test.getName(), productFromDb.getName());
        assertEquals(p1Test.getDescription(), productFromDb.getDescription());
        assertEquals(p1Test.getPrice(), productFromDb.getPrice());
        assertEquals(p1Test.getOtherAttributesMap().size(), productFromDb.getOtherAttributesMap().size());
        verify(productRepository).findAll();
    }

    @Test
    void givenValidProduct_whenCreateProduct_thenSaveAndReturnProduct() {
        Product inputProduct = new Product(
                new Producer("Samsung"),
                "SAMSUNG QE98QN990F 98",
                "Samsung TV",
                new BigDecimal("4999.99"),
                new HashMap<>());

        when(productRepository.save(any())).thenReturn(inputProduct);

        Product savedProduct = productService.createProduct(new Product(
                new Producer("Samsung"),
                "SAMSUNG QE98QN990F 98",
                "Samsung TV",
                new BigDecimal("4999.99"),
                new HashMap<>()));


        assertEquals(inputProduct.getProducer().getName(), savedProduct.getProducer().getName());
        assertEquals(inputProduct.getName(), savedProduct.getName());
        assertEquals(inputProduct.getDescription(), savedProduct.getDescription());
        assertEquals(inputProduct.getPrice(), savedProduct.getPrice());
        assertEquals(inputProduct.getOtherAttributesMap().size(), savedProduct.getOtherAttributesMap().size());
        verify(productRepository).save(any());
    }

    @Test
    void givenNullProduct_whenCreateProduct_thenThrowIllegalArgumentException() {
        Product inputProduct = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct)
        );
    }
    @Test
    void givenProductWithBlankAttribute_whenCreateProduct_thenThrow() {
        Product inputProduct = new Product(new Producer(""),"", "",  new BigDecimal(""), new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct));
    }
}
