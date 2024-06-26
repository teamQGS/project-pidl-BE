package com.example.demo.mappers;
import com.example.demo.DTOS.records.SignUpDTO;
import com.example.demo.DTOS.records.UpdateUserDTO;
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