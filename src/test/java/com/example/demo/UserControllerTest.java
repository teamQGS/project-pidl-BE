package com.example.demo;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.DTOS.records.LoginDTO;
import com.example.demo.DTOS.records.SignUpDTO;
import com.example.demo.DTOS.records.UpdatePasswordDTO;
import com.example.demo.DTOS.records.UpdateUserDTO;
import com.example.demo.controllers.UserController;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuthProvider userAuthProvider;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    public void testRegister() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("testUser", "testUser", "password".toCharArray());
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setUsername("testUser");

        when(userService.register(any(SignUpDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(2)
    public void testLogout() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userAuthProvider.validateToken(any(String.class))).thenReturn(authentication); // mock the validation of the token
        when(authentication.getPrincipal()).thenReturn(userDTO); // mock getting the principal from the authentication
        when(userService.logout(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/logout")
                        .header("Authorization", "Bearer testToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }


    @Test
    @Order(3)
    public void testLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO("testUser", "password".toCharArray());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userService.login(any(LoginDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(4)
    public void testUpdateUser() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("newFirstName", "newLastName", "newEmail", "newPhone");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userService.updateUser(any(UpdateUserDTO.class), any(String.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/update/{username}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(5)
    public void testChangePassword() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("password".toCharArray(), "zaza".toCharArray());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userService.changePassword(any(UpdatePasswordDTO.class), any(String.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/changePassword/{username}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(6)
    public void testGetUserByUsername() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userService.getUserByUsername("testUser")).thenReturn(Optional.of(userDTO));

        mockMvc.perform(get("/api/users/find/{username}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(7)
    public void testDeleteUserByUsername() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userService.deleteUserByUsername("testUser")).thenReturn(Optional.of(userDTO));

        mockMvc.perform(delete("/api/users/delete/{username}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @Order(8)
    public void testNotGetUserByUsername() throws Exception {
        String username = "testUser";

        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/find/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
