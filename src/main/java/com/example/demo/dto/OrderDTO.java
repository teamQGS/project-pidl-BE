package com.example.demo.dto;

import com.example.demo.model.entities.enums.Status;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
     long id;
     Date date;
     String customerUsername;
     String managerUsername;
     List<ProductDTO> products;
     double totalSum;
     AddressDTO addressDTO;
     Status status;
}
