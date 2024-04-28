package com.example.demo.DTOS;

import lombok.Data;


@Data
public class ProductDTO {
    private String _id;
    private String name;
    private String description;
    private double price;
    private String illustration;
    private WarehouseDTO warehouse;
}