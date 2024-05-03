package com.example.demo.services;

import com.example.demo.DTOS.CartDTO;
import com.example.demo.DTOS.ProductDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.CartRepository;
import com.example.demo.model.Repositories.ProductRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<CartDTO> getAllCarts(){
        List<CartEntity> cartEntities = cartRepository.findAll();
        return cartEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }
    public CartDTO convertToDTO(CartEntity cartEntity){
        return modelMapper.map(cartEntity, CartDTO.class);
    }

    public CartDTO getCartByUsername(String username){
        Optional<CartEntity> cart = cartRepository.findByUsername(username);
        if(cart.isEmpty()){
            throw new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(cart, CartDTO.class);
    }

    public CartDTO addToCart(String productId, String username){
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found!", HttpStatus.NOT_FOUND));

        CartEntity cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND));

        List<ProductEntity> currentProducts = cart.getProducts();

        if (currentProducts == null) {
            currentProducts = new LinkedList<>();
        }

        boolean productExistsInCart = false;

        for (ProductEntity p : currentProducts) {
            if (p.getId().equals(productId)) {
                p.setCount(p.getCount() + 1);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            product.setCount(1);
            currentProducts.add(product);
        }

        cart.setProducts(currentProducts);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);
    }


    public CartDTO removeFromCart(String productId, String username){
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found!", HttpStatus.NOT_FOUND));

        CartEntity cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND));

        List<ProductEntity> currentProducts = cart.getProducts();

        if (currentProducts == null) {
            currentProducts = new LinkedList<>();
        }

        currentProducts.removeIf(p -> p.getId().equals(productId));

        cart.setProducts(currentProducts);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);
    }

    public CartDTO clearCart(String username){

        CartEntity cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND));

        List<ProductEntity> currentProducts = new LinkedList<>();
        cart.setProducts(currentProducts);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);
    }
}
