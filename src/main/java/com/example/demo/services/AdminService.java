package com.example.demo.services;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
        System.out.println(role);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        user.setRole(Role.valueOf(role));
        System.out.println("Role set");
        UserEntity saved = userRepository.save(user);
        System.out.println("User saved");
        return userMapper.toUserDTO(saved);
    }

}
