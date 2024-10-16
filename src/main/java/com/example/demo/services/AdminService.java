package com.example.demo.services;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper userMapper;


    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public UserDTO convertToDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public UserDTO changeRole(String role, String username){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        if(Objects.equals(role, user.getRole().toString())){
            throw new AppException("User already has this role", HttpStatus.CONFLICT);
        }
        else {
            user.setRole(Role.valueOf(role));
        }
        UserEntity saved = userRepository.save(user);
        return userMapper.toUserDTO(saved);
    }

    public Optional<UserDTO> deleteUserById(Long id){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        optionalUserEntity.ifPresent(userEntity -> {
            userRepository.deleteById(id);
            System.out.println("User with ID: " + id + " was deleted!");
        });

        userRepository.deleteById(id);
        return optionalUserEntity.map(userEntity -> modelMapper.map(optionalUserEntity, UserDTO.class));
    }

}
