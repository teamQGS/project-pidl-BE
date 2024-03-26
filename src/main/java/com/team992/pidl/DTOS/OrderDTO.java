package com.team992.pidl.DTOS;

import lombok.Data;

import java.util.Date;
import java.util.List;

import com.team992.pidl.Model.Entities.Enums.Status;

@Data
public class OrderDTO {
    String id;
    Date date;
    String userID; //or UserDTO userDTO, because we can get id from userDTO
    String StaffID; //or StaffDTO staffDTO
    List<String> productIds;
    List<Integer> count;
    double totalSum;
    String addressId;
    Status status;
}