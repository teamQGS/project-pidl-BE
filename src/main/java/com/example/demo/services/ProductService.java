package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.model.repositories.CartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.entities.ProductEntity;
import com.example.demo.model.entities.enums.ProductsCategory;
import com.example.demo.model.repositories.ProductRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private final CartRepository cartRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public List<ProductDTO> getAllProducts(){
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public ProductDTO convertToDTO(ProductEntity productEntity){
        return modelMapper.map(productEntity, ProductDTO.class);
    }
    public Optional<ProductDTO> getProductById(long id){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        return optionalProductEntity.map(ProductEntity -> modelMapper.map(optionalProductEntity, ProductDTO.class));
    }


    public Optional<ProductDTO> deleteProductById(long id){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        optionalProductEntity.ifPresent(productEntity -> {
            productRepository.deleteById(id);
            System.out.println("Product with ID: " + id + " was deleted!");
        });

        productRepository.deleteById(id);

        return optionalProductEntity.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class));
    }
    public ProductDTO createProduct(ProductDTO productDTO){
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public ProductEntity updateProduct(long id, ProductDTO updatedProduct){
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setName(updatedProduct.getName());
            productEntity.setPrice(updatedProduct.getPrice());
            productEntity.setDescription(productEntity.getDescription());
            productEntity.setIllustration(updatedProduct.getIllustration());
            productEntity.setProductCategory(updatedProduct.getProductCategory());
            return productRepository.save(productEntity);
        }else{
            System.out.println("There is no product with ID: " + id);
            return null;
        }
    }

    public List<ProductDTO> findProductsByCategory(ProductsCategory productsCategory){
        List<ProductEntity> productEntities = productRepository.findAllByProductCategory(productsCategory);
        return productEntities.stream().map(this::convertToDTO).toList();
    }

    public List<ProductDTO> findProductByName(String name) {
        String jpql = "SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))";
        TypedQuery<ProductEntity> query = entityManager.createQuery(jpql, ProductEntity.class);
        query.setParameter("name", name);
        List<ProductEntity> productEntities = query.getResultList();
        return productEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all categories
    public ProductsCategory[] getCategories(){
        return ProductsCategory.values();
    }
}
