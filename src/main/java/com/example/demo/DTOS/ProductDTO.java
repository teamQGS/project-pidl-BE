package com.example.demo.DTOS;

import lombok.Data;


@Data
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String illustration;
    private int count;
}