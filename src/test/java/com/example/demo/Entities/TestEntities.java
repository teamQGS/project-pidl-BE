package com.example.demo.Entities;

import com.example.demo.model.Entities.*;
import com.example.demo.model.Entities.Enums.Status;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

// This class is used to create entities for testing purposes
public class TestEntities {

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
        ProductEntity product = new ProductEntity();
        product.setId(new ObjectId());
        product.setName("testProductName");
        product.setDescription("testDescription");
        product.setPrice(100);
        product.setIllustration("testIllustration");
        product.setWarehouse(createWarehouse());
        return product;
    }

    public static WarehouseEntity createWarehouse() {
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(new ObjectId());
        warehouse.setCount(100);
        warehouse.setTotalByes(1000);
        warehouse.setProductId(new ObjectId());
        return warehouse;
    }

    public static AddressEntity createAddress() {
        AddressEntity address = new AddressEntity();
        address.setId(new ObjectId());
        address.setUserId(createUser());
        address.setCity("testCity");
        address.setStreet("testStreet");
        address.setHouse("testHouse");
        address.setPostcode("testPostcode");
        address.setCountrycode("testCountrycode");
        return address;
    }

    public static OrderEntity createOrder() {
        OrderEntity order = new OrderEntity();
        order.setId(new ObjectId());
        order.setDate(new Date());
        order.setUserId(createUser());
        order.setProductIds(Arrays.asList(createProduct()));
        order.setCount(Arrays.asList(1)); // Здесь мы предполагаем, что у вас есть один продукт каждого типа в заказе
        order.setTotalSum(100);
        order.setAddressId(createAddress());
        order.setStatus(Status.PAID);
        return order;
    }

    public static ContactEntity createContact() {
        ContactEntity contact = new ContactEntity();
        contact.setId(new ObjectId());
        contact.setUserId(createUser());
        contact.setEmail("test@example.com");
        contact.setPhone("1234567890");
        return contact;
    }
}