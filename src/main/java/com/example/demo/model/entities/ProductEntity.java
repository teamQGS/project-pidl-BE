package com.example.demo.model.entities;

import com.example.demo.model.entities.enums.ProductsCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String description;

    private double price;

    private String illustration; //Now it is a link to illustration. TODO create image variable

    private int count;

    @Enumerated(EnumType.STRING)
    private ProductsCategory productCategory;

//    private Binary illustration;

}