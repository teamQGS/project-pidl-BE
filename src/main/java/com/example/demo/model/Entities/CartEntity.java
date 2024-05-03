package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    @Id
    private String id;
    private String username;
    private List<ProductEntity> products;
}
