package com.example.demo.DTOS;

import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.model.Entities.ProductEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
     String id;
     Date date;
     String customerUsername;
     String managerUsername;
     List<ProductDTO> products;
     double totalSum;
     AddressDTO addressDTO;
     Status status;
}
