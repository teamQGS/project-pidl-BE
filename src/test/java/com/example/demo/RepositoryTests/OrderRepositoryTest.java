package com.example.demo.RepositoryTests;

import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.model.Repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import com.example.demo.Entities.TestEntities;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: Fix org.springframework.dao.DataIntegrityViolationException caused by the old staff fields in database
// WAITING FOR THE FIX
@DataMongoTest
@TestPropertySource(locations = "classpath:application.test.properties")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        OrderEntity order = TestEntities.createOrder();

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