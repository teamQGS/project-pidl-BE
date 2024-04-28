package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Status;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String id;
    private Date date;
    private UserDTO userID;
    private List<ProductDTO> productIds;
    private List<ProductDTO> count;
    private int totalSum;
    private ObjectId addressId;
    private Status status;
}
