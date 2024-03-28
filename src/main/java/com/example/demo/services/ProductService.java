package com.example.demo.services;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;
    @Autowired
     ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts(){
        List<ProductEntity> productEntities = repository.findAll();
        return productEntities.stream()
                .map(ProductEntity -> modelMapper.map(productEntities, ProductDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<ProductDTO> getProductById(ObjectId id){
        Optional<ProductEntity> optionalProductEntity = repository.findById(id);
        return optionalProductEntity.map(ProductEntity -> modelMapper.map(optionalProductEntity, ProductDTO.class));
    }
    public Optional<ProductDTO> deleteProductById(ObjectId id){
        Optional<ProductEntity> optionalProductEntity = repository.findById(id);

        optionalProductEntity.ifPresent(productEntity -> {
            repository.deleteById(id);
            System.out.println("Product with ID: " + id + " was deleted!");
        });

        return optionalProductEntity.map(ProductEntity-> modelMapper.map(optionalProductEntity, ProductDTO.class));
    }
    public ProductDTO createProduct(ProductDTO productDTO){
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        ProductEntity savedProduct = repository.save(productEntity);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
