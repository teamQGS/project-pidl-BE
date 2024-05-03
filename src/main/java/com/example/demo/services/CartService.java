package com.example.demo.services;

import com.example.demo.DTOS.CartDTO;
import com.example.demo.DTOS.ProductDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.CartRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
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
    //todo add to card and remove from cart clear cart;
}
