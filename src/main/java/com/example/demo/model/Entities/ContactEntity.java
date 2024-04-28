package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contacts")
public class ContactEntity {
    @Id
    private String id;
    @DocumentReference
    private UserEntity userId;
    private String email;
    private String phone;
}