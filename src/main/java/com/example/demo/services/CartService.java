package com.example.demo.services;

import com.example.demo.DTOS.CartDTO;
import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.ProductEntity;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public List<CartDTO> getAllCarts() {
        logger.info("Retrieving all carts from the database");
        List<CartEntity> cartEntities = cartRepository.findAll();
        logger.info("Mapping all carts to DTOs");
        return cartEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CartDTO convertToDTO(CartEntity cartEntity) {
        logger.info("Converting CartEntity to CartDTO for cartId: {}", cartEntity.getId());
        return modelMapper.map(cartEntity, CartDTO.class);
    }

    public CartDTO getCartByUsername(String username) {
        if (username == null) {
            logger.error("Attempted to retrieve cart with null username");
            throw new AppException("Username must not be null", HttpStatus.BAD_REQUEST);
        }
        logger.info("Retrieving cart for username: {}", username);
        Optional<CartEntity> cart = cartRepository.findByUsername(username);
        if (cart.isEmpty()) {
            logger.error("No cart found for username: {}", username);
            throw new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND);
        }
        logger.info("Cart found for username: {}, converting to DTO", username);
        return modelMapper.map(cart.get(), CartDTO.class);
    }
    

    public CartDTO addToCart(String productId, String username) {
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

    public CartDTO removeFromCart(String productId, String username) {
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

    public CartDTO clearCart(String username) {

        CartEntity cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("This cart doesn't exist!", HttpStatus.NOT_FOUND));

        List<ProductEntity> currentProducts = new LinkedList<>();
        cart.setProducts(currentProducts);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);
    }
}
