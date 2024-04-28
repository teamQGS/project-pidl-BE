package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "warehouse")
public class WarehouseEntity {

    @Id
    private String _id;

    private int count;

    private int totalByes;

    //get id from product

    private String productId;


}