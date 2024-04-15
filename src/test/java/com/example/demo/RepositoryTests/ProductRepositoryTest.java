package com.example.demo.RepositoryTests;

import com.example.demo.Entities.TestEntities;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        ProductEntity product = TestEntities.createProduct();

        productRepository.save(product);

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);
        assertEquals(product.getName(), foundProduct.getName());
    }

    @Test
    public void testFindProductById() {
        ProductEntity product = TestEntities.createProduct();

        productRepository.save(product);

        Optional<ProductEntity> foundProduct = productRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
    }

    @Test
    public void testDeleteProduct() {
        ProductEntity product = TestEntities.createProduct();

        productRepository.save(product);
        productRepository.delete(product);

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);
        assertTrue(foundProduct == null);
    }

    @Test
    public void testUpdateProduct() {
        ProductEntity product = TestEntities.createProduct();

        productRepository.save(product);

        product.setName("updatedName");
        productRepository.save(product);

        ProductEntity foundProduct = productRepository.findById(product.getId()).orElse(null);
        assertEquals("updatedName", foundProduct.getName());
    }
}