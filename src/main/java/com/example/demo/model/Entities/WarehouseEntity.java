package com.example.demo.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Warehouse")
public class WarehouseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int count;

    private int totalByes;

    //get id from product
    @OneToMany(mappedBy="warehouse")
    private Set<ProductEntity> products;

}