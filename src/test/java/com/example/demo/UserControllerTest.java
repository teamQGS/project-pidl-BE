package com.example.demo;

import com.example.demo.Controllers.UserController;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void registerUserTest() throws Exception {
        UserDTO userDTO = new UserDTO();
        Long expectedId = 1L;
        given(userService.createUser(any(UserDTO.class))).willReturn(expectedId);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedId.toString()));
    }

    @Test
    public void deleteUserTest() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        given(userService.deleteUser(userId)).willReturn(userDTO);

        mockMvc.perform(delete("/api/users/delete/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));
    }
}