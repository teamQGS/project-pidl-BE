package com.example.demo.services;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.WarehouseEntity;
import com.example.demo.model.Repositories.ProductRepository;
import com.example.demo.model.Repositories.WarehouseRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
     ModelMapper modelMapper;
    @Autowired
    MongoTemplate mongoTemplate;

    public List<ProductDTO> getAllProducts(){
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(ProductEntity -> modelMapper.map(productEntities, ProductDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<ProductDTO> getProductById(ObjectId id){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        return optionalProductEntity.map(ProductEntity -> modelMapper.map(optionalProductEntity, ProductDTO.class));
    }
    public Optional<ProductDTO> deleteProductById(ObjectId id){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        optionalProductEntity.ifPresent(productEntity -> {
            productRepository.deleteById(id);
            System.out.println("Product with ID: " + id + " was deleted!");
        });

        mongoTemplate.remove(Query.query(Criteria.where("productId").is(id)), WarehouseEntity.class);

        return optionalProductEntity.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class));
    }
    public ProductDTO createProduct(ProductDTO productDTO, int quantity){
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        ProductEntity savedProduct = productRepository.insert(productEntity);
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setCount(quantity);
        warehouseEntity.setProductId(savedProduct.getId());
        warehouseRepository.save(warehouseEntity);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public ProductEntity updateProduct(ObjectId id, ProductDTO updatedProduct){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setName(updatedProduct.getName());
            productEntity.setPrice(updatedProduct.getPrice());
            productEntity.setDescription(productEntity.getDescription());
            productEntity.setIllustration(updatedProduct.getIllustration());
            return productRepository.save(productEntity);
        }else{
            System.out.println("There is no product with ID: " + id);
            return null;
        }
    }
}
