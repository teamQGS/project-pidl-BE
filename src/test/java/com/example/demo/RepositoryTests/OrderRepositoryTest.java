package com.example.demo.RepositoryTests;


import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.model.Repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import com.example.demo.Entities.TestEntities;
import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Repositories.AddressRepository;
import com.example.demo.model.Repositories.ProductRepository;
import com.example.demo.model.Repositories.UserRepository;
import com.example.demo.model.Entities.UserEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: Fix org.springframework.dao.DataIntegrityViolationException caused by the old staff fields in database
// WAITING FOR THE FIX
@DataMongoTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
public void testSaveOrder() {
    UserEntity user = userRepository.save(TestEntities.createUser());
    ProductEntity product = productRepository.save(TestEntities.createProduct());
    AddressEntity address = TestEntities.createAddress(user);  // Передаём сохранённого пользователя

    addressRepository.save(address);

    OrderEntity order = TestEntities.createOrder();
    order.setUserId(user);
    order.setProductIds(Arrays.asList(product));
    order.setAddressId(address);

    orderRepository.save(order);

    OrderEntity foundOrder = orderRepository.findById(order.getId()).orElse(null);
    assertThat(foundOrder).isNotNull();
    assertThat(foundOrder).isEqualToComparingFieldByField(order);
}


    @Test
    public void testFindOrderById() {
        OrderEntity order = TestEntities.createOrder();

        orderRepository.save(order);

        Optional<OrderEntity> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get()).isEqualToComparingFieldByField(order);
    }

    @Test
    public void testUpdateOrder() {
        OrderEntity order = TestEntities.createOrder();

        orderRepository.save(order);

        order.setTotalSum(200);
        orderRepository.save(order);

        OrderEntity foundOrder = orderRepository.findById(order.getId()).orElse(null);
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getTotalSum()).isEqualTo(200);
    }

    @Test
    public void testDeleteOrder() {
        OrderEntity order = TestEntities.createOrder();

        orderRepository.save(order);
        orderRepository.delete(order);

        OrderEntity foundOrder = orderRepository.findById(order.getId()).orElse(null);
        assertThat(foundOrder).isNull();
    }
}