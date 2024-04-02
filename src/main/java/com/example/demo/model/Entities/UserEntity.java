package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private ObjectId id;

    private int _numericalId;

    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private Boolean isAdult;

    private String image; //Now it is a link to image. TODO create image variable
}