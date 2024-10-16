package com.example.demo;

import com.example.demo.DTOS.FeedbackDTO;
import com.example.demo.controllers.FeedbackController;
import com.example.demo.services.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeedbackControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    public void testGetAllFeedbacks() throws Exception {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(1);
        feedbackDTO.setUsername("testUser");
        feedbackDTO.setSubject("Test Subject");
        feedbackDTO.setFeedbackContent("Test Content");
        feedbackDTO.setDate("2024-05-27");
        feedbackDTO.setEmail("test@example.com");

        List<FeedbackDTO> feedbackList = Collections.singletonList(feedbackDTO);

        when(feedbackService.getAllFeedbacks()).thenReturn(feedbackList);

        mockMvc.perform(get("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[0].subject").value("Test Subject"))
                .andExpect(jsonPath("$[0].feedbackContent").value("Test Content"))
                .andExpect(jsonPath("$[0].date").value("2024-05-27"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    @Order(2)
    public void testAddFeedback() throws Exception {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(1);
        feedbackDTO.setUsername("testUser");
        feedbackDTO.setSubject("Test Subject");
        feedbackDTO.setFeedbackContent("Test Content");
        feedbackDTO.setDate("2024-05-27");
        feedbackDTO.setEmail("test@example.com");

        when(feedbackService.createProduct(any(FeedbackDTO.class))).thenReturn(feedbackDTO);

        mockMvc.perform(post("/api/feedback/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.feedbackContent").value("Test Content"))
                .andExpect(jsonPath("$.date").value("2024-05-27"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}

