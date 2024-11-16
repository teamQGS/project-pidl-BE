package com.example.demo.services;

import com.example.demo.dto.AddressDTO;
import com.example.demo.model.entities.AddressEntity;
import com.example.demo.model.repositories.AddressRepository;
import com.example.demo.security.config.AppException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressService {

    private final AddressRepository addressRepository;

    private final ModelMapper modelMapper;

    public AddressDTO convertToDTO(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, AddressDTO.class);
    }

    public AddressDTO findAddressByUsername(String username){
        Optional<AddressEntity> address = addressRepository.findByUsername(username);
        if (address.isEmpty()) {
            throw new AppException("This address doesn't exist!", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(address.get(), AddressDTO.class);
    }

    public AddressDTO updateAddress(AddressDTO addressDTO, String username) {
        System.out.println(addressDTO);
        Optional<AddressEntity> existingAddressOpt = addressRepository.findByUsername(username);
        if (existingAddressOpt.isEmpty()) {
            throw new AppException("This address doesn't exist!", HttpStatus.NOT_FOUND);
        }

        AddressEntity existingAddress = existingAddressOpt.get();
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setHouse(addressDTO.getHouse());
        existingAddress.setPostcode(addressDTO.getPostcode());
        existingAddress.setCountrycode(addressDTO.getCountrycode());

        AddressEntity updatedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }
}

