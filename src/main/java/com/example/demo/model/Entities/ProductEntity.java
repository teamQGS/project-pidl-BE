package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.ProductsCategory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;


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

    private ProductsCategory productCategory;

//    private Binary illustration;

}