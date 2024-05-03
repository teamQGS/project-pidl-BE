package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Status;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
     String id;
     Date date;
     UserDTO userID;
     List<ProductDTO> productIds;
     List<ProductDTO> count;
     int totalSum;
     ObjectId addressId;
     Status status;
}
