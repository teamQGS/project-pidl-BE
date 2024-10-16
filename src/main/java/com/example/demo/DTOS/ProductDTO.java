package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.ProductsCategory;
import lombok.Data;


@Data
public class ProductDTO {
     long id;
     String name;
     String description;
     double price;
     String illustration;
     int count;
     ProductsCategory productCategory;
//     Binary illustration;
}