package com.example.demo.security.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class AuthUser {
    @Id
    public ObjectId id;
    @Indexed
    public String username;
    public String firstName;
    public String lastName;
    public int age;
    public String password;
    public boolean isActive;
}
