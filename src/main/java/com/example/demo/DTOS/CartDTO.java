package com.example.demo.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    String id;
    String username;
    List<ProductDTO> products;
}
