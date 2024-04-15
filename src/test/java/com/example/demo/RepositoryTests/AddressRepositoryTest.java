package com.example.demo.RepositoryTests;

import com.example.demo.Entities.TestEntities;
import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Fix org.springframework.dao.DataIntegrityViolationException caused by the old staff and location fields in database
// WAITING FOR THE FIX
@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSaveAddress() {
        AddressEntity address = TestEntities.createAddress();

        addressRepository.save(address);

        AddressEntity foundAddress = addressRepository.findById(address.getId()).orElse(null);
        assertEquals(address.getCity(), foundAddress.getCity());
        assertEquals(address.getStreet(), foundAddress.getStreet());
        assertEquals(address.getHouse(), foundAddress.getHouse());
        assertEquals(address.getPostcode(), foundAddress.getPostcode());
        assertEquals(address.getCountrycode(), foundAddress.getCountrycode());
    }

    @Test
    public void testFindAddressById() {
        AddressEntity address = TestEntities.createAddress();

        addressRepository.save(address);

        Optional<AddressEntity> foundAddress = addressRepository.findById(address.getId());
        assertTrue(foundAddress.isPresent());
        assertEquals(address.getCity(), foundAddress.get().getCity());
        assertEquals(address.getStreet(), foundAddress.get().getStreet());
        assertEquals(address.getHouse(), foundAddress.get().getHouse());
        assertEquals(address.getPostcode(), foundAddress.get().getPostcode());
    }

    @Test
    public void testDeleteAddress() {
        AddressEntity address = TestEntities.createAddress();

        addressRepository.save(address);
        addressRepository.delete(address);

        AddressEntity foundAddress = addressRepository.findById(address.getId()).orElse(null);
        assertTrue(foundAddress == null);
    }
}