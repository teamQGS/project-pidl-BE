package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.Enums.ProductsCategory;
import com.example.demo.model.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    public List<ProductEntity> findAllByProductCategory(ProductsCategory productsCategory);
}