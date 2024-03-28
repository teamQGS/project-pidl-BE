package com.example.demo.services;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
      UserRepository repository;
    @Autowired
    ModelMapper modelMapper;
    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntities = repository.findAll();
        return userEntities.stream()
                .map(UserEntity -> modelMapper.map(userEntities, UserDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<UserDTO> getUserById(int id){
        Optional<UserEntity> userEntity = repository.findBy_numericalId(id);
        return userEntity.map(UserEntity-> modelMapper.map(userEntity, UserDTO.class));
    }
}
