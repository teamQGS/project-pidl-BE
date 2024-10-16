package com.example.demo.services;

import com.example.demo.dto.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.entities.Enums.Role;
import com.example.demo.model.entities.ProductEntity;
import com.example.demo.model.entities.UserEntity;
import com.example.demo.model.repositories.UserRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    @Autowired
    private MongoTemplate mongoTemplate;


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

    public Optional<UserDTO> deleteUserById(String id){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        optionalUserEntity.ifPresent(userEntity -> {
            userRepository.deleteById(id);
            System.out.println("User with ID: " + id + " was deleted!");
        });

        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), ProductEntity.class);

        return optionalUserEntity.map(userEntity -> modelMapper.map(optionalUserEntity, UserDTO.class));
    }

}
