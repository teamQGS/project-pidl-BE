package com.example.demo.controllers;


import com.example.demo.dto.AddressDTO;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{username}")
    public ResponseEntity<AddressDTO> getUserAddress(@PathVariable String username){
        return new ResponseEntity<>(addressService.findAddressByUsername(username), HttpStatus.OK);
    }
    @PutMapping("/update/{username}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO, @PathVariable String username) {
        AddressDTO updatedAddress = addressService.updateAddress(addressDTO, username);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
}
