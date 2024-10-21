package com.example.demo;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.entities.enums.Status;
import com.example.demo.controllers.OrderController;
import com.example.demo.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    public void testGetOrderById() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDate(new Date());
        orderDTO.setCustomerUsername("customer");
        orderDTO.setManagerUsername("manager");
        orderDTO.setProducts(Collections.emptyList());
        orderDTO.setTotalSum(100.0);
        orderDTO.setAddressDTO(new AddressDTO());
        orderDTO.setStatus(Status.PENDING);

        when(orderService.findOrderById(1)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerUsername").value("customer"))
                .andExpect(jsonPath("$.managerUsername").value("manager"))
                .andExpect(jsonPath("$.totalSum").value(100.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @Order(2)
    public void testFindByCustomerUsername() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDate(new Date());
        orderDTO.setCustomerUsername("customer");
        orderDTO.setManagerUsername("manager");
        orderDTO.setProducts(Collections.emptyList());
        orderDTO.setTotalSum(100.0);
        orderDTO.setAddressDTO(new AddressDTO());
        orderDTO.setStatus(Status.PENDING);

        when(orderService.findActiveOrderByCustomerUsername("customer")).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/find/{username}", "customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerUsername").value("customer"))
                .andExpect(jsonPath("$.managerUsername").value("manager"))
                .andExpect(jsonPath("$.totalSum").value(100.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @Order(3)
    public void testCreateOrder() throws Exception {
        AddressDTO addressDTO = new AddressDTO();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDate(new Date());
        orderDTO.setCustomerUsername("customer");
        orderDTO.setManagerUsername("manager");
        orderDTO.setProducts(Collections.emptyList());
        orderDTO.setTotalSum(100.0);
        orderDTO.setAddressDTO(addressDTO);
        orderDTO.setStatus(Status.PENDING);

        when(orderService.createOrder(anyString(), any(AddressDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders/create/{username}", "customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addressDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerUsername").value("customer"))
                .andExpect(jsonPath("$.managerUsername").value("manager"))
                .andExpect(jsonPath("$.totalSum").value(100.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @Order(4)
    public void testGetOrdersByUsername() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDate(new Date());
        orderDTO.setCustomerUsername("customer");
        orderDTO.setManagerUsername("manager");
        orderDTO.setProducts(Collections.emptyList());
        orderDTO.setTotalSum(100.0);
        orderDTO.setAddressDTO(new AddressDTO());
        orderDTO.setStatus(Status.PENDING);

        List<OrderDTO> orders = Collections.singletonList(orderDTO);

        when(orderService.getOrdersByUsername("customer")).thenReturn(orders);

        mockMvc.perform(get("/api/orders/findAll/{username}", "customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].customerUsername").value("customer"))
                .andExpect(jsonPath("$[0].managerUsername").value("manager"))
                .andExpect(jsonPath("$[0].totalSum").value(100.0))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    @Order(5)
    public void testChangeStatus() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDate(new Date());
        orderDTO.setCustomerUsername("customer");
        orderDTO.setManagerUsername("manager");
        orderDTO.setProducts(Collections.emptyList());
        orderDTO.setTotalSum(100.0);
        orderDTO.setAddressDTO(new AddressDTO());
        orderDTO.setStatus(Status.COMPLETED);

        when(orderService.changeStatus(anyString(), Long.parseLong(anyString()))).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/changeStatus/{orderId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"COMPLETED\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerUsername").value("customer"))
                .andExpect(jsonPath("$.managerUsername").value("manager"))
                .andExpect(jsonPath("$.totalSum").value(100.0))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
