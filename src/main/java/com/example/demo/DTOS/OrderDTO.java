package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Status;
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
