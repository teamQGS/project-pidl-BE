package com.example.demo.services;

import com.example.demo.DTOS.AddressDTO;
import com.example.demo.DTOS.CartDTO;
import com.example.demo.DTOS.OrderDTO;
import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Entities.CartEntity;
import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.model.Repositories.OrderRepository;
import com.example.demo.model.Repositories.ProductRepository;
import com.example.demo.security.config.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartService cartService;

    public OrderDTO convertToDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public OrderDTO findOrderById(String id){
        Optional<OrderEntity> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new AppException("This order doesn't exist!", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(order.get(), OrderDTO.class);
    }

    public OrderDTO createOrder(String username, AddressDTO addressDTO){
        Optional<OrderEntity> activeOrder = orderRepository.findByCustomerUsernameAndStatus(username, Status.IN_PROCESS);
        if (activeOrder.isPresent()) {
            throw new AppException("You already have an active order in process", HttpStatus.BAD_REQUEST);
        }

        CartDTO cartDTO = cartService.getCartByUsername(username);
        if (cartDTO.getProducts().isEmpty()) {
            throw new AppException("Cart is empty for username: " + username, HttpStatus.BAD_REQUEST);
        }
        List<ProductEntity> productEntities = cartDTO.getProducts().stream()
                .map(productDTO -> modelMapper.map(productDTO, ProductEntity.class))
                .collect(Collectors.toList());

        for (ProductEntity product : productEntities) {
            ProductEntity productEntity = productRepository.findById(product.getId())
                    .orElseThrow(() -> new AppException("Product not found: " + product.getId(), HttpStatus.NOT_FOUND));
            if (productEntity.getCount() < product.getCount()) {
                throw new AppException("Not enough products in stock for: " + product.getName(), HttpStatus.BAD_REQUEST);
            }
            productEntity.setCount(productEntity.getCount() - product.getCount());
            productRepository.save(productEntity);
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(new Date());
        orderEntity.setCustomerUsername(username);
        orderEntity.setProducts(productEntities);
        orderEntity.setTotalSum(calculateTotalSum(productEntities));
        orderEntity.setStatus(Status.IN_PROCESS);
        orderEntity.setAddressEntity(modelMapper.map(addressDTO, AddressEntity.class));
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    private double calculateTotalSum(List<ProductEntity> products) {
        double totalSum = 0.0;
        for (ProductEntity product : products) {
            totalSum += product.getPrice();
        }
        return totalSum;
    }

}