package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderEntity {

    @Id
    private String id;
    private Date date;
    private String customerUsername;
    private String managerUsername;
    private List<ProductEntity> products;
    private double totalSum;
    private AddressEntity addressEntity;
    private Status status;
}