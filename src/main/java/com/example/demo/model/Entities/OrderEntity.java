package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private Date date;
    private String customerUsername;
    private String managerUsername;
    private List<ProductEntity> products;
    private double totalSum;
    private AddressEntity addressEntity;
    private Status status;
}