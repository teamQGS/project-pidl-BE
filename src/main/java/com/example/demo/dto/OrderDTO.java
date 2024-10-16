package com.example.demo.dto;

import com.example.demo.model.entities.Enums.Status;
import lombok.Data;

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
