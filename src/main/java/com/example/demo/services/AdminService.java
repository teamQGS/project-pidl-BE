package com.example.demo.services;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.entities.enums.Role;
import com.example.demo.model.entities.UserEntity;
import com.example.demo.model.repositories.UserRepository;
import com.example.demo.security.config.AppException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
        return modelMapper.map(saved, UserDTO.class);
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
