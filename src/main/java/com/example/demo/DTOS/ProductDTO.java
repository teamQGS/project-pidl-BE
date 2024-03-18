package com.example.demo.DTOS;

import lombok.Data;

@Data
public class ProductDTO {
    Long id;
    String name;
    String description;
    String authorLastName;
    double price;
    String illustration;
    WarehouseDTO warehouseDTO;
}