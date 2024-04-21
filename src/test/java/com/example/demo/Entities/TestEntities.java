package com.example.demo.Entities;

import com.example.demo.model.Entities.*;
import com.example.demo.model.Entities.Enums.Status;
import org.bson.types.ObjectId;
import org.mapstruct.control.MappingControl.Use;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class TestEntities {
    private static UserEntity testUser = createUser();
    private static AddressEntity testAddress = createAddress(testUser);
    private static ProductEntity testProduct = createProduct();
    private static WarehouseEntity testWarehouse = createWarehouse();

    // Ensure that all entities are initialized only once and then reused
    static {
        testWarehouse.setProductId(testProduct.getId());
        testProduct.setWarehouse(testWarehouse);
        testAddress.setUserId(testUser);
    }

    public static UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setId(new ObjectId());
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles(new HashSet<>());
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setToken("testToken");
        user.setAge(30);
        user.setEmail("test@test.com");
        user.setIsAdult(true);
        user.setImage("testImage");
        return user;
    }

    public static ProductEntity createProduct() {
        return new ProductEntity(new ObjectId(), "testProductName", "testDescription", 100, "testIllustration", null);
    }

    public static WarehouseEntity createWarehouse() {
        return new WarehouseEntity(new ObjectId(), 100, 1000, null);
    }

    public static AddressEntity createAddress(UserEntity user) {
        AddressEntity address = new AddressEntity();
        address.setId(new ObjectId());
        address.setUserId(user);  // Устанавливаем userId после сохранения UserEntity
        address.setCity("testCity");
        address.setStreet("testStreet");
        address.setHouse("testHouse");
        address.setPostcode("testPostcode");
        address.setCountrycode("testCountrycode");
        return address;
    }
    

    public static OrderEntity createOrder() {
        return new OrderEntity(new ObjectId(), new Date(), testUser, Arrays.asList(testProduct), Arrays.asList(1), 100, testAddress, Status.PAID);
    }

    public static ContactEntity createContact() {
        return new ContactEntity(new ObjectId(), testUser, "test@example.com", "1234567890");
    }
}
