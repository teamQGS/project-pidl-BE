package com.example.demo.model.entities;

import com.example.demo.model.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Date date;
    private String customerUsername;
    private String managerUsername;
    @OneToMany
    private List<ProductEntity> products;
    private double totalSum;
    @ManyToOne
    @JoinColumn(name = "address_entity_id")
    private AddressEntity addressEntity;
    private Status status;
}