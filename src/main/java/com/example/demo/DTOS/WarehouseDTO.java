package com.example.demo.DTOS;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class WarehouseDTO {
    private String _id;
    private int count;
    private int totalByes;
    private ObjectId productId;
}
