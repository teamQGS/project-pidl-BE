package com.example.demo.dto;

import com.example.demo.model.entities.Enums.ProductsCategory;
import lombok.Data;


@Data
public class ProductDTO {
     String id;
     String name;
     String description;
     double price;
     String illustration;
     int count;
     ProductsCategory productCategory;
//     Binary illustration;
}