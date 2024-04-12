package com.example.demo.security.persistence;

import com.example.demo.model.Entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id

    private ObjectId id;

    private String token;

    private UserEntity user;

    private LocalDateTime createdAt;

}
