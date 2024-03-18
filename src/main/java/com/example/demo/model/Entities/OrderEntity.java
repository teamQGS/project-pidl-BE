package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Status;
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
@Table(name = "Orders")
public class OrderEntity {

    //Ešte nie som istý, aká má byť štkruktúra.

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    private Long staffId;

    private Long userId;

    @ElementCollection
    private List<Long> productIds;

    @ElementCollection
    private List<Integer> count;

    private double totalSum;

    private Long addressId;

    private Status status;
}