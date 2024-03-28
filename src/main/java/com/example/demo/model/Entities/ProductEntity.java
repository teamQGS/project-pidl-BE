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
@Document(collection = "products")
public class ProductEntity {

    @Id
    private ObjectId id;

    private String name;

    private String description;

    private int price;

    private String illustration; //Now it is a link to illustration. TODO create image variable

    @DocumentReference
    private WarehouseEntity warehouse;

}