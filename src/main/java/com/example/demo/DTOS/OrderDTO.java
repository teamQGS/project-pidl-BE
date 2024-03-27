package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Status;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    String id;
    Date date;
    Long userID; //or UserDTO userDTO, because we can get id from userDTO
    Long StaffID; //or StaffDTO staffDTO
    List<Long> productIds;
    List<Integer> count;
    double totalSum;
    Long addressId;
    Status status;
}
