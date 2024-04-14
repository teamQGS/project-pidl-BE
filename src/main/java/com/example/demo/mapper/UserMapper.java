package com.example.demo.mapper;
import com.example.demo.DTOS.SignUpDTO;
import com.example.demo.DTOS.UpdateUserDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDTO signUpDto);

    UserEntity updateToUser(UpdateUserDTO updateUserDTO);
}