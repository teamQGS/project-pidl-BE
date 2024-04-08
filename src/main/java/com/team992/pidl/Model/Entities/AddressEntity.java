package com.team992.pidl.Model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.util.Set;

@Document(collection = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    private String id;

    private String address; // Represents a string that can be divided into more detailed parts such as street, house number, etc.

    // It isn't good idea (get information about User or Staff) for SQL, but for MongoDB it's fine.
    // get id from User
    // private Set<String> userIds;

    // get id from Staff
    // private Set<String> staffIds;
}