package com.example.demo;

import com.example.demo.dto.CartDTO;
import com.example.demo.controllers.CartController;
import com.example.demo.services.CartService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    public void testGetUserCart() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.getCartByUsername("testUser")).thenReturn(cartDTO);

        mockMvc.perform(get("/api/cart/{username}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(2)
    public void testAddToCart() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.addToCart(anyString(), anyString())).thenReturn(cartDTO);

        mockMvc.perform(put("/api/cart/{username}/add", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"productId\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(3)
    public void testRemoveFromCart() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.removeFromCart(anyString(), anyString())).thenReturn(cartDTO);

        mockMvc.perform(put("/api/cart/{username}/remove", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"productId\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(4)
    public void testClearCart() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.clearCart("testUser")).thenReturn(cartDTO);

        mockMvc.perform(put("/api/cart/{username}/clear", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(5)
    public void testIncreaseQuantity() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.increaseCount(anyString(), anyString())).thenReturn(cartDTO);

        mockMvc.perform(put("/api/cart/{username}/increase", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"productId\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(6)
    public void testDecreaseQuantity() throws Exception {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId("1");
        cartDTO.setUsername("testUser");
        cartDTO.setProducts(Collections.emptyList());

        when(cartService.decreaseCount(anyString(), anyString())).thenReturn(cartDTO);

        mockMvc.perform(put("/api/cart/{username}/decrease", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"productId\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }
}
