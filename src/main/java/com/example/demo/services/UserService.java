package com.example.demo.services;

import com.example.demo.DTOS.LoginDTO;
import com.example.demo.DTOS.SignUpDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import com.example.demo.security.config.AppException;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.security.persistence.RoleEntity;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private UserAuthProvider userAuthProvider;

    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntities = repository.findAll();
        return userEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO convertToDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Optional<UserDTO> getUserById(ObjectId id){
        Optional<UserEntity> userEntity = repository.findById(id);
        return userEntity.map(UserEntity-> modelMapper.map(userEntity, UserDTO.class));
    }

    public UserDTO login(LoginDTO loginDTO){
        UserEntity user = repository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDTO.password()), user.getPassword())) {
            System.out.println("login successful " + loginDTO.username());
            String token = userAuthProvider.createToken(user);
            user.setToken(token);
            UserEntity saved = repository.save(user);
            return userMapper.toUserDTO(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO signUpDTO){
        Optional<UserEntity> user = repository.findByUsername(signUpDTO.username());
    
        if(user.isPresent()) {
            throw new AppException("Username exists", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userMapper.signUpToUser(signUpDTO);
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        
        // Assign the role of CUSTOMER to the user
        RoleEntity customerRole = new RoleEntity();
        customerRole.setRoleName(Role.CUSTOMER.toString()); // Set the role name to CUSTOMER
        userEntity.getRoles().add(customerRole);
        
        String token = userAuthProvider.createToken(userEntity);
        userEntity.setToken(token);
        UserEntity saved = repository.save(userEntity);
        return userMapper.toUserDTO(saved);
    }

    public UserDTO logout(UserDTO dto){
        Optional<UserEntity> optionalUser = repository.findByUsername(dto.getUsername());
        if(optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setToken(null);
            repository.save(user);
            System.out.println("User with username " + user.getUsername() + " logged out");
            return dto;
        } else {
            return null;
        }
    }

    // This method checks if a user has a role
    public boolean userHasRole(String username, Role role) {
        Optional<UserEntity> user = repository.findByUsername(username);
        return user.isPresent() && user.get().getRoles().contains(role);
    }

    // This method assigns a role to a user
    public void assignRole(String username, Role role) {
        UserEntity user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(role.name());
        user.getRoles().add(roleEntity);
    
        repository.save(user);
    }
}