package com.example.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.example.demo.DTOS.OrderDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.services.ProductService;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthProvider userAuthProvider;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    // Method to get all orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestHeader(value = "Authorization", required = false) String authentication) {
        if (authentication != null) {
            String[] authArray = authentication.split(" ");
            Authentication auth = userAuthProvider.validateToken(authArray[1]);
            UserDTO userDTO = (UserDTO) auth.getPrincipal();
            if (userService.userHasRole(userDTO.getUsername(), Role.MANAGER)) {
                List<OrderDTO> orders = orderService.getAllOrders().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orders);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Method to get order by id
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String orderId, @RequestBody Status status, @RequestHeader(value = "Authorization", required = false) String authentication) {
        if (authentication != null) {
            String[] authArray = authentication.split(" ");
            Authentication auth = userAuthProvider.validateToken(authArray[1]);
            UserDTO userDTO = (UserDTO) auth.getPrincipal();
            if (userService.userHasRole(userDTO.getUsername(), Role.MANAGER)) {
                OrderDTO updatedOrder = convertToDTO(orderService.updateOrderStatus(orderId, status));
                return ResponseEntity.ok(updatedOrder);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Method to create an order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, @RequestHeader(value = "Authorization", required = false) String authentication) {
        if (authentication != null) {
            String[] authArray = authentication.split(" ");
            Authentication auth = userAuthProvider.validateToken(authArray[1]);
            UserDTO userDTO = (UserDTO) auth.getPrincipal();
            orderDTO.setUserID(userDTO); // Set the user who created the order

            OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
            OrderEntity savedOrderEntity = orderService.saveOrder(orderEntity);
            OrderDTO savedOrderDTO = convertToDTO(savedOrderEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public OrderDTO convertToDTO(OrderEntity orderEntity){
    OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);

    // Convert user id to DTO
    UserDTO userDTO = userService.convertToDTO(orderEntity.getUserId());
    orderDTO.setUserID(userDTO);

    // Convert product ids to DTO
    List<ProductDTO> productDTOs = orderEntity.getProductIds().stream()
        .map(productService::convertToDTO)
        .collect(Collectors.toList());
    orderDTO.setProductIds(productDTOs);

    return orderDTO;
    }  
}