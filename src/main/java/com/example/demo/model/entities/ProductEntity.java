package com.example.demo.model.entities;

import com.example.demo.model.entities.Enums.ProductsCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductEntity {

    @Id
    private String id;

    private String name;

    private String description;

    private double price;

    private String illustration; //Now it is a link to illustration. TODO create image variable

    private int count;

    private ProductsCategory productCategory;

//    private Binary illustration;

}