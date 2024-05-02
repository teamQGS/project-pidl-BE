package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.Enums.ProductsCategory;
import com.example.demo.model.Entities.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    public List<ProductEntity> findAllByProductCategory(ProductsCategory productsCategory);
}