package com.example.demo.DTOS;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ProductDTO {
    private ObjectId id;
    private String name;
    private String description;
    private double price;
    private String illustration;
    private WarehouseDTO warehouse;
}