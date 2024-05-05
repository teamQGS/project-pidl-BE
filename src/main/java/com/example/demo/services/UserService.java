package com.example.demo.services;

import com.example.demo.DTOS.*;
import com.example.demo.DTOS.records.LoginDTO;
import com.example.demo.DTOS.records.SignUpDTO;
import com.example.demo.DTOS.records.UpdatePasswordDTO;
import com.example.demo.DTOS.records.UpdateUserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.CartRepository;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Optional<UserDTO> getUserById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.ifPresent(user -> logger.info("Retrieved user by id: {}", user.getId()));
        return userEntity.map(UserEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        userEntity.ifPresent(user -> logger.info("Retrieved user by username: {}", user.getUsername()));
        return userEntity.map(UserEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    public UserDTO login(LoginDTO loginDTO) {
        logger.info("Attempting to login user: {}", loginDTO.username());
        UserEntity user = userRepository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new AppException("Invalid credentials", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDTO.password()), user.getPassword())) {
            logger.info("Login successful, generating token for user: {}", loginDTO.username());
            String token = userAuthProvider.createToken(user);
            user.setToken(token);
            UserEntity saved = userRepository.save(user);
            return userMapper.toUserDTO(user);
        }
        logger.warn("Login failed for user: {}", loginDTO.username());
        throw new AppException("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO signUpDTO) {
        logger.info("Attempting to find if user exists: {}", signUpDTO.username());
        Optional<UserEntity> user = userRepository.findByUsername(signUpDTO.username());

        if (user.isPresent()) {
            logger.warn("Registration failed, username already exists: {}", signUpDTO.username());
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        // Создание пользователя
        logger.info("Registering new user: {}", signUpDTO.username());
        UserEntity userEntity = userMapper.signUpToUser(signUpDTO);
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        userEntity.setRole(Role.CUSTOMER);

        // Сохранение пользователя
        UserEntity savedUser = userRepository.save(userEntity);

        // Создание корзины для пользователя
        logger.info("User registered, creating cart: {}", savedUser.getUsername());
        CartEntity userCart = new CartEntity();
        userCart.setUsername(savedUser.getUsername());
        cartRepository.save(userCart);

        // Создание токена и обновление пользователя с токеном
        String token = userAuthProvider.createToken(savedUser);
        savedUser.setToken(token);
        UserEntity updatedUser = userRepository.save(savedUser);

        logger.info("User registered and token generated: {}", updatedUser.getUsername());
        return userMapper.toUserDTO(updatedUser);
    }

    public UserDTO logout(UserDTO dto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setToken(null);
            userRepository.save(user);
            logger.info("User with username {} logged out", dto.getUsername());
            return dto;
        } else {
            logger.warn("Logout attempt for non-existin user: {}", dto.getUsername());
            return null;
        }
    }

    public Optional<UserDTO> deleteUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        user.ifPresent(userEntity -> {
            userRepository.deleteByUsername(username);
            logger.info("User with username: {} was deleted!", username);
        });
        mongoTemplate.remove(Query.query(Criteria.where("username").is(username)), UserEntity.class);

        return user.map(userEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        logger.info("Updating user profile for username: {}", username);

        if (!Objects.equals(updateUserDTO.firstName(), "")) {
            user.setFirstName(updateUserDTO.firstName());
            logger.info("Updated first name to: {}", updateUserDTO.firstName());
        }
        if (!Objects.equals(updateUserDTO.lastName(), "")) {
            user.setLastName(updateUserDTO.lastName());
            logger.info("Updated last name to: {}", updateUserDTO.lastName());
        }
        if (!Objects.equals(updateUserDTO.email(), "")) {
            user.setEmail(updateUserDTO.email());
            logger.info("Updated email to: {}", updateUserDTO.email());
        }
        if (!Objects.equals(updateUserDTO.address(), "")) {
            user.setAddress(updateUserDTO.address());
            logger.info("Updated address to: {}", updateUserDTO.address());
        }
        if (!Objects.equals(updateUserDTO.phoneNumber(), "")) {
            user.setPhoneNumber(updateUserDTO.phoneNumber());
            logger.info("Updated phone number to: {}", updateUserDTO.phoneNumber());
        }
        UserEntity saved = userRepository.save(user);
        return userMapper.toUserDTO(saved);
    }

    public UserDTO changePassword(UpdatePasswordDTO updatePasswordDTO, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(updatePasswordDTO.currentPassword()), user.getPassword())) {
            logger.info("Changing password for user: {}", username);
            user.setPassword(passwordEncoder.encode(CharBuffer.wrap(updatePasswordDTO.newPassword())));
            UserEntity saved = userRepository.save(user);
            return userMapper.toUserDTO(saved);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
