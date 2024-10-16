package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    long id;
    String username;
    List<ProductDTO> products;
}
