package com.team992.pidl.DTOS;

import lombok.Data;

@Data
public class ProductDTO {
    String id;
    String name;
    String description;
    String authorLastName;
    double price;
    String illustration;
    String warehouseId;
}