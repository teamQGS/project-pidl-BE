package com.example.demo.model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.util.Set;

@Document(collection = "Contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {
    @Id
    private String id;

    private String email;

    private String phoneNumber;

    // It isn't good idea (get information about User or Staff) for SQL, but for MongoDB it's fine.
    // get id from User
    // private Set<String> userIds;

    // get id from Staff
    // private Set<String> staffIds;
}