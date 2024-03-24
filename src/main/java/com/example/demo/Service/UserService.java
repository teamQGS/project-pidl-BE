package com.example.demo.Service;

import com.example.demo.DTOS.LoginDTO;
import com.example.demo.DTOS.SignUpDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.exceptions.AppException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Entities.UserEntity;
import com.example.demo.model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;

    public UserDTO login(LoginDTO loginDTO){
        UserEntity user = userRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(loginDTO.password()), user.getPassword())) {
            System.out.println("login successful " + loginDTO.email());
            return userMapper.toUserDTO(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDTO register(SignUpDTO signUpDTO){
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.email());

        if(user.isPresent()) {
            throw new AppException("Email exists", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userMapper.signUpToUser(signUpDTO);
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        UserEntity saved = userRepository.save(userEntity);
        return userMapper.toUserDTO(saved);
    }




    //    public Long createUser(UserDTO userDTO){
//        UserEntity entity = new UserEntity();
//        entity.setUsername(userDTO.getUsername());
//        entity.setEmail(userDTO.getEmail());
//        entity.setPassword(userDTO.getPassword());
//        userRepository.save(entity);
//        return entity.getId();
//    }


//    public UserDTO deleteUser(Long id){
//        Optional<UserEntity> optEntity = userRepository.findById(id);
//        if (optEntity.isEmpty()) {
//            System.out.println("There is no book with id: " + id);
//        }
//        UserEntity userEntity = optEntity.get();
//        userRepository.deleteById(id);
//        System.out.println("Book with id: " + id + " was deleted!");
//
//        UserDTO dto = new UserDTO();
//        dto.setId(userEntity.getId());
//        dto.setUsername(userEntity.getUsername());
//        dto.setEmail(userEntity.getEmail());
//        return dto;
//    }
}
