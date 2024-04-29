package com.example.demo.services;

import com.example.demo.DTOS.*;
import com.example.demo.DTOS.records.LoginDTO;
import com.example.demo.DTOS.records.SignUpDTO;
import com.example.demo.DTOS.records.UpdatePasswordDTO;
import com.example.demo.DTOS.records.UpdateUserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import com.example.demo.security.config.AppException;
import com.example.demo.security.config.UserAuthProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Objects;
import java.util.Optional;

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
    @Autowired
    MongoTemplate mongoTemplate;

    public UserDTO convertToDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Optional<UserDTO> getUserById(String id){
        Optional<UserEntity> userEntity = repository.findById(id);
        return userEntity.map(UserEntity-> modelMapper.map(userEntity, UserDTO.class));
    }

    public Optional<UserDTO> getUserByUsername(String username){
        Optional<UserEntity> userEntity = repository.findByUsername(username);
        return userEntity.map(UserEntity-> modelMapper.map(userEntity, UserDTO.class));
    }

    public UserDTO login(LoginDTO loginDTO){
        UserEntity user = repository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new AppException("Invalid credentials", HttpStatus.NOT_FOUND));


        if (passwordEncoder.matches(CharBuffer.wrap(loginDTO.password()), user.getPassword())) {
            System.out.println("login successful " + loginDTO.username());
            String token = userAuthProvider.createToken(user);
            user.setToken(token);
            UserEntity saved = repository.save(user);
            return userMapper.toUserDTO(user);
        }
        throw new AppException("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO signUpDTO){
        Optional<UserEntity> user = repository.findByUsername(signUpDTO.username());

        if(user.isPresent()) {

            throw new AppException("Username exists", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userMapper.signUpToUser(signUpDTO);
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        userEntity.setRole(Role.CUSTOMER);

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

    public Optional<UserDTO> deleteUserByUsername(String username){
        Optional<UserEntity> user = repository.findByUsername(username);
        user.ifPresent(userEntity -> {
            repository.deleteByUsername(username);
            System.out.println("User with username: " + username + " was deleted!");
        });
        mongoTemplate.remove(Query.query(Criteria.where("username").is(username)), UserEntity.class);

        return user.map(userEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    public UserDTO updateUser (UpdateUserDTO updateUserDTO, String username){
        UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        if (!Objects.equals(updateUserDTO.firstName(), "")) {
            user.setFirstName(updateUserDTO.firstName());
        }
        if (!Objects.equals(updateUserDTO.lastName(), "")) {
            user.setLastName(updateUserDTO.lastName());
        }
        if (!Objects.equals(updateUserDTO.email(), "")) {
            user.setEmail(updateUserDTO.email());
        }
        if (!Objects.equals(updateUserDTO.address(), "")) {
            user.setAddress(updateUserDTO.address());
        }
        if (!Objects.equals(updateUserDTO.phoneNumber(), "")) {
            user.setPhoneNumber(updateUserDTO.phoneNumber());
        }
        UserEntity saved = repository.save(user);
        return userMapper.toUserDTO(saved);
    }

    public UserDTO changePassword (UpdatePasswordDTO updatePasswordDTO, String username){
        UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(updatePasswordDTO.currentPassword()), user.getPassword())) {
            System.out.println(updatePasswordDTO.currentPassword());
            System.out.println(updatePasswordDTO.newPassword());
            System.out.println("Password changed successfully " + username);
            user.setPassword(passwordEncoder.encode(CharBuffer.wrap(updatePasswordDTO.newPassword())));
            UserEntity saved = repository.save(user);
            return userMapper.toUserDTO(saved);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }


//    public boolean userHasRole(String username, Role role) {
//        Optional<UserEntity> user = repository.findByUsername(username);
//        return user.isPresent() && user.get().getRole().getAuthorities();
//    }

    // This method assigns a role to a user
//    public void assignRole(String username, Role role) {
//        UserEntity user = repository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//        user.setRole(role);
//
//        repository.save(user);
//    }

    // This method removes a role from a user
//    public void removeRole(String username, Role role) {
//        UserEntity user = repository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//        RoleEntity roleEntity = user.getAuthorities().stream()
//                .filter(r -> r.().equals(role.name()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Role Not Found: " + role));
//
//        user.getRoles().remove(roleEntity);
//
//        repository.save(user);
//    }
}
