package com.team992.pidl.Model.Entities;

import com.team992.pidl.Model.Entities.Enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntity {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Role role;

    private Boolean isAviable;

    private String image; // Now it is a link to image. TODO create image variable

    // get id from Contact
    // private String contactId;
    // private String addressId;
}