package com.example.demo.mappers;
import com.example.demo.dto.records.SignUpDTO;
import com.example.demo.dto.records.UpdateUserDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDTO signUpDto);

    UserEntity updateToUser(UpdateUserDTO updateUserDTO);
}