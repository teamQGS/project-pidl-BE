package com.example.demo.model.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Warehouse")
public class WarehouseEntity {

    @Id
    private String id;

    private Integer count;

    private Integer totalByes;

    //get id from product
    //private Set<String> products;

    //getters and setters
}