package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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

    @DocumentReference
    private UserEntity userId;

    @DocumentReference
    private List<ProductEntity> productIds;
    // @DocumentReference
    // private List<ProductEntity> count;

    private List<Integer> count; // not tested

    private int totalSum;
    @DocumentReference
    private AddressEntity addressId;

    private Status status;
}