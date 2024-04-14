package com.example.demo.model.Entities;

import com.example.demo.security.persistence.RoleEntity;
import com.example.demo.security.persistence.TokenEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private Set<RoleEntity> roles = new HashSet<>();
    private String firstName;
    private String lastName;

    @DBRef
    private TokenEntity tokenEntity;
    private String token;
    private int age;
    private Boolean isAdult;
    private String image; //Now it is a link to image. TODO create image variable
}