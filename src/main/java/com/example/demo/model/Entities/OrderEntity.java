package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Document(collection = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private String id;

    private Date date;

    private String staffId;

    private String userId;

    private List<String> productIds;

    private List<Integer> count;

    private double totalSum;

    private String addressId;

    private Status status;
}