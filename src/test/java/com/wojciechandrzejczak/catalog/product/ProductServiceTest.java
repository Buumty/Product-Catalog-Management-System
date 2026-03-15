package com.wojciechandrzejczak.catalog.product;

import com.wojciechandrzejczak.catalog.producer.Producer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenNoProductsInDatabase_whenFindAllProducts_thenReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> allProducts = productService.findAllProducts();

        assertEquals(0, allProducts.size());
        verify(productRepository,times(1)).findAll();
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
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenValidProducerName_whenFindAllProductsByProducerName_thenReturnGivenProducerProducts() {
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

        List<Product> samsungProductList = new ArrayList<>();
        samsungProductList.add(p1Test);
        samsungProductList.add(p2Test);

        when(productRepository.findByProducer_Name("Samsung")).thenReturn(samsungProductList);

        List<Product> allProductsByProducerName = productService.findAllProductsByProducerName("Samsung");

        assertEquals(samsungProductList.size(), allProductsByProducerName.size());
        verify(productRepository, times(1)).findByProducer_Name("Samsung");
    }

    @Test
    void givenBlankProducerName_whenFindAllProductsByProducerName_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.findAllProductsByProducerName(""));
    }

    @Test
    void givenNullProducerName_whenFindAllProductsByProducerName_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> productService.findAllProductsByProducerName(null)
        );
    }

    @Test
    void givenValidProduct_whenCreateProduct_thenSaveAndReturnProduct() {
        Product inputProduct = new Product(
                new Producer("Samsung"),
                "SAMSUNG QE98QN990F 98",
                "Samsung TV",
                new BigDecimal("4999.99"),
                new HashMap<>());

        when(productRepository.save(any(Product.class))).thenReturn(inputProduct);

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
        verify(productRepository, times(1)).save(any(Product.class));
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
    void givenProductWithBlankName_whenCreateProduct_thenThrowIllegalArgumentException() {
        Product inputProduct = new Product(new Producer("Samsung"),"", "description",  new BigDecimal("10.00"), new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct));
    }

    @Test
    void givenProductWithBlankProducerName_whenCreateProduct_thenThrowIllegalArgumentException() {
        Product inputProduct = new Product(new Producer(""),"Smartphone Samsung Galaxy A5", "description",  new BigDecimal("10.00"), new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct));
    }
    @Test
    void givenProductWithBlankDescription_whenCreateProduct_thenThrowIllegalArgumentException() {
        Product inputProduct = new Product(new Producer("Samsung"),"Smartphone Samsung Galaxy A5", "",  new BigDecimal("10.00"), new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct));
    }

    @Test
    void givenProductWithPriceBelowZero_whenCreateProduct_thenThrowIllegalArgumentException() {
        Product inputProduct = new Product(new Producer("Samsung"),"Smartphone Samsung Galaxy A5", "description",  new BigDecimal("-10.00"), new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(inputProduct));
    }

    @Test
    void givenValidNewProduct_whenUpdateProduct_thenReturnUpdatedProduct() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer("Apple"),
                "Smartphone Apple Iphone 13",
                "Apple Phone", new BigDecimal("1999.99"),
                new HashMap<>());


        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        Product updatedProduct = productService.updateProduct(1L, newProduct);


        assertEquals(newProduct.getProducer().getName(), updatedProduct.getProducer().getName());
        assertEquals(newProduct.getName(), updatedProduct.getName());
        assertEquals(newProduct.getDescription(), updatedProduct.getDescription());
        assertEquals(newProduct.getPrice(), updatedProduct.getPrice());
        assertEquals(newProduct.getOtherAttributesMap(), updatedProduct.getOtherAttributesMap());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistingId_whenUpdateProduct_thenThrowNoSuchElementException() {
        Long nonExistingId = 1000L;

        Product newProduct = new Product(
                new Producer("Apple"),
                "Smartphone Apple Iphone 13",
                "Apple Phone", new BigDecimal("1999.99"),
                new HashMap<>());

        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> productService.updateProduct(nonExistingId, newProduct));

        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void givenProductWithBlankProducerName_whenUpdateProduct_thenThrowIllegalArgumentException() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer(""),
                "Smartphone Apple Iphone 13",
                "Apple Phone", new BigDecimal("1999.99"),
                new HashMap<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, newProduct));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void givenProductWithBlankName_whenUpdateProduct_thenThrowIllegalArgumentException() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer("Apple"),
                "",
                "Apple Phone", new BigDecimal("1999.99"),
                new HashMap<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, newProduct));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void givenProductWithBlankDescription_whenUpdateProduct_thenThrowIllegalArgumentException() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer("Apple"),
                "Smartphone Apple Iphone 13",
                "", new BigDecimal("1999.99"),
                new HashMap<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, newProduct));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void givenProductWithNullPrice_whenUpdateProduct_thenThrowIllegalArgumentException() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer("Apple"),
                "Smartphone Apple Iphone 13",
                "Apple Phone", null,
                new HashMap<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, newProduct));

        verify(productRepository, times(1)).findById(1L);
    }
    @Test
    void givenProductWithPriceBelowZero_whenUpdateProduct_thenThrowIllegalArgumentException() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        Product newProduct = new Product(
                new Producer("Apple"),
                "Smartphone Apple Iphone 13",
                "Apple Phone", new BigDecimal("-1000.00"),
                new HashMap<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1L, newProduct));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidId_whenDeleteProductById_thenDeleteProduct() {
        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    void givenNonExistingId_whenDeleteProductById_thenThrowNoSuchElementException() {
        Long nonExistingId = 1000L;

        Product existingProduct = new Product(
                new Producer("Samsung"),
                "Smartphone Samsung Galaxy A5", "Samsung Phone",
                new BigDecimal("999.99"),
                new HashMap<>());
        existingProduct.setId(1L);

        assertThrows(NoSuchElementException.class,
                () -> productService.deleteProduct(nonExistingId));

        verify(productRepository, times(1)).findById(1000L);
        verify(productRepository, times(0)).delete(existingProduct);
    }
}
