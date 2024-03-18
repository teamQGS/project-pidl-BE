package com.example.demo.DTOS;

import lombok.Data;

@Data
public class WarehouseDTO {
    Long id;
    int count;
    int totalByes;
    ProductDTO productDTO;
}
