package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Role;
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
@Document(collection = "staff")
public class StaffEntity {
    @Id
    private ObjectId id;

    private String firstName;

    private String lastName;

    private Role role;

    private Boolean isAviable;

    private String image; //Now it is a link to image. TODO create image variable


    @DocumentReference
    private ContactEntity contact;
    @DocumentReference
    private AddressEntity address;

}