package com.example.demo.Model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;

    private String name;

    private String description;

    private double price;

    private String illustration; // Now it is a link to illustration. TODO create image variable

    // get id from Warehouse
    // private String warehouseId;
}