package com.example.demo.mappers;

import com.example.demo.dto.*;
import com.example.demo.dto.records.LoginDTO;
import com.example.demo.dto.records.SignUpDTO;
import com.example.demo.model.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);

        modelMapper.createTypeMap(ProductEntity.class, ProductDTO.class);
        modelMapper.createTypeMap(UserEntity.class, UserDTO.class);
        modelMapper.createTypeMap(UserEntity.class, LoginDTO.class);
        modelMapper.createTypeMap(UserEntity.class, SignUpDTO.class);
        modelMapper.createTypeMap(CartEntity.class, CartDTO.class);
        modelMapper.createTypeMap(OrderEntity.class, OrderDTO.class);
        modelMapper.createTypeMap(AddressEntity.class, AddressDTO.class);
        modelMapper.createTypeMap(FeedbackEntity.class, FeedbackDTO.class);
        return modelMapper;
    }
}
