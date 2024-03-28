package com.example.demo.mapper;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.DTOS.WarehouseDTO;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Entities.WarehouseEntity;
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
        modelMapper.createTypeMap(WarehouseEntity.class, WarehouseDTO.class);

        return modelMapper;
    }
}
