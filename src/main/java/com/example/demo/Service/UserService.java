package com.example.demo.Service;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.Model.Entities.UserEntity;
import com.example.demo.Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Long createUser(UserDTO userDTO){
        UserEntity entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());
        userRepository.save(entity);
        return entity.getId();
    }

    public UserDTO deleteUser(Long id){
        Optional<UserEntity> optEntity = userRepository.findById(id);
        if (optEntity.isEmpty()) {
            System.out.println("There is no book with id: " + id);
        }
        UserEntity userEntity = optEntity.get();
        userRepository.deleteById(id);
        System.out.println("Book with id: " + id + " was deleted!");

        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setPassword(userEntity.getPassword());
        return dto;
    }
}
