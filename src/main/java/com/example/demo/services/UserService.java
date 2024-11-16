package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.dto.records.LoginDTO;
import com.example.demo.dto.records.SignUpDTO;
import com.example.demo.dto.records.UpdatePasswordDTO;
import com.example.demo.dto.records.UpdateUserDTO;
import com.example.demo.model.entities.AddressEntity;
import com.example.demo.model.entities.CartEntity;
import com.example.demo.model.entities.enums.Role;
import com.example.demo.model.entities.UserEntity;
import com.example.demo.model.repositories.AddressRepository;
import com.example.demo.model.repositories.CartRepository;
import com.example.demo.model.repositories.UserRepository;
import com.example.demo.security.config.AppException;
import com.example.demo.security.config.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthProvider userAuthProvider;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Optional<UserDTO> getUserById(Long id) {
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
        UserEntity userEntity = userRepository.findByUsername(loginDTO.username())
                .orElseThrow(() -> new AppException("Invalid credentials", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDTO.password()), userEntity.getPassword())) {
            logger.info("Login successful, generating token for user: {}", loginDTO.username());
            String token = userAuthProvider.createToken(userEntity);
            userEntity.setToken(token);
            UserEntity saved = userRepository.save(userEntity);
            return modelMapper.map(saved, UserDTO.class);
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

        logger.info("Registering new user: {}", signUpDTO.username());
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpDTO.email());
        userEntity.setUsername(signUpDTO.username());

        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        userEntity.setRole("ROLE_ADMIN");

        userRepository.save(userEntity);

        logger.info("User registered, creating cart: {}", userEntity.getUsername());
        CartEntity userCart = new CartEntity();
        userCart.setUsername(userEntity.getUsername());
        cartRepository.save(userCart);

        // Создание адреса для пользователя
        logger.info("User registered, creating address: {}", userEntity.getUsername());
        AddressEntity userAddress = new AddressEntity();
        userAddress.setUsername(userEntity.getUsername());
        addressRepository.save(userAddress);

        // Создание токена и обновление пользователя с токеном
        String token = userAuthProvider.createToken(userEntity);
        System.out.println("Token: " + token);
        userEntity.setToken(token);
        UserEntity updatedUser = userRepository.save(userEntity);

        logger.info("User registered and token generated: {}", updatedUser.getUsername());
        return modelMapper.map(updatedUser, UserDTO.class);
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
            cartRepository.deleteCartEntityByUsername(username);
            addressRepository.deleteAddressEntityByUsername(username);
            logger.info("User with username: {} was deleted!", username);
        });
        return user.map(userEntity -> modelMapper.map(userEntity, UserDTO.class));
    }


    public UserDTO updateUser(UpdateUserDTO updateUserDTO, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        logger.info("Updating user profile for username: {}", username);

        // Update fields if provided
        Optional.ofNullable(updateUserDTO.firstName()).ifPresent(name -> {
            user.setFirstName(name);
            logger.info("Updated first name to: {}", name);
        });

        Optional.ofNullable(updateUserDTO.lastName()).ifPresent(name -> {
            user.setLastName(name);
            logger.info("Updated last name to: {}", name);
        });

        Optional.ofNullable(updateUserDTO.email()).ifPresent(email -> {
            user.setEmail(email);
            logger.info("Updated email to: {}", email);
        });

        Optional.ofNullable(updateUserDTO.phoneNumber()).ifPresent(phone -> {
            user.setPhoneNumber(phone);
            logger.info("Updated phone number to: {}", phone);
        });

        UserEntity saved = userRepository.save(user);
        return modelMapper.map(saved, UserDTO.class);
    }


    public UserDTO changePassword(UpdatePasswordDTO updatePasswordDTO, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(updatePasswordDTO.currentPassword()), user.getPassword())) {
            logger.info("Changing password for user: {}", username);
            user.setPassword(passwordEncoder.encode(CharBuffer.wrap(updatePasswordDTO.newPassword())));
            UserEntity saved = userRepository.save(user);
            return modelMapper.map(saved, UserDTO.class);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
