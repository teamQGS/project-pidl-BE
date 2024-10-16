package com.example.demo;

import com.example.demo.dto.ProductDTO;
import com.example.demo.controllers.ProductController;
import com.example.demo.model.entities.Enums.ProductsCategory;
import com.example.demo.model.entities.ProductEntity;
import com.example.demo.services.ProductService;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    public void testGetAllProducts() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        List<ProductDTO> productList = Collections.singletonList(productDTO);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].illustration").value("Test Illustration"))
                .andExpect(jsonPath("$[0].count").value(10))
                .andExpect(jsonPath("$[0].productCategory").value("ADULT"));
    }

    @Test
    @Order(2)
    public void testGetProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        when(productService.getProductById("1")).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/products/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.illustration").value("Test Illustration"))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.productCategory").value("ADULT"));
    }

    @Test
    @Order(3)
    public void testDeleteProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        when(productService.deleteProductById("1")).thenReturn(Optional.of(productDTO));

        mockMvc.perform(delete("/api/products/delete/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.illustration").value("Test Illustration"))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.productCategory").value("ADULT"));
    }

    @Test
    @Order(4)
    public void testAddProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.illustration").value("Test Illustration"))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.productCategory").value("ADULT"));
    }

    @Test
    @Order(5)
    public void testUpdateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(150.0);
        productDTO.setIllustration("Updated Illustration");
        productDTO.setCount(20);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        ProductEntity productEntity = new ProductEntity("1", "Updated Product", "Updated Description", 150.0, "Updated Illustration", 20, ProductsCategory.ADULT);

        when(productService.updateProduct(eq("1"), any(ProductDTO.class))).thenReturn(productEntity);

        mockMvc.perform(put("/api/products/update/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.price").value(150.0))
                .andExpect(jsonPath("$.illustration").value("Updated Illustration"))
                .andExpect(jsonPath("$.count").value(20))
                .andExpect(jsonPath("$.productCategory").value("ADULT"));
    }

    @Test
    @Order(6)
    public void testGetProductsByCategory() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        List<ProductDTO> productList = Collections.singletonList(productDTO);

        when(productService.findProductsByCategory(ProductsCategory.ADULT)).thenReturn(productList);

        mockMvc.perform(get("/api/products/category/{category}", ProductsCategory.ADULT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].illustration").value("Test Illustration"))
                .andExpect(jsonPath("$[0].count").value(10))
                .andExpect(jsonPath("$[0].productCategory").value("ADULT"));
    }

    @Test
    @Order(7)
    public void testSearchProductByName() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("1");
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setIllustration("Test Illustration");
        productDTO.setCount(10);
        productDTO.setProductCategory(ProductsCategory.ADULT);

        List<ProductDTO> productList = Collections.singletonList(productDTO);

        when(productService.findProductByName("Test Product")).thenReturn(productList);

        mockMvc.perform(get("/api/products/search")
                        .param("name", "Test Product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].illustration").value("Test Illustration"))
                .andExpect(jsonPath("$[0].count").value(10))
                .andExpect(jsonPath("$[0].productCategory").value("ADULT"));
    }

    @Test
    @Order(8)
    public void testGetCategories() throws Exception {
        ProductsCategory[] categories = ProductsCategory.values();

        mockMvc.perform(get("/api/products/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(categories[0].toString()));
    }
}
