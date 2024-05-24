package com.example.demo.controllers;


import com.example.demo.DTOS.AddressDTO;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{username}")
    public ResponseEntity<AddressDTO> getUserAddress(@PathVariable String username){
        return new ResponseEntity<>(addressService.findAddressByUsername(username), HttpStatus.OK);
    }
}
