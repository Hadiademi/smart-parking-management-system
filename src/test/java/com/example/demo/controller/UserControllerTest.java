package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setName("Test User");
        testUserDTO.setEmail("test@example.com");
        testUserDTO.setPassword("password123");
        testUserDTO.setRole(User.UserRole.CLIENT);
    }

    @Test
    void createUser_Success() throws Exception {
        // Arrange
        when(userService.createUser(any(UserDTO.class))).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void getUserById_Success() throws Exception {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getAllUsers_Success() throws Exception {
        // Arrange
        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setEmail("test2@example.com");
        user2.setPassword("password456");
        user2.setRole(User.UserRole.CLIENT);

        List<UserDTO> users = Arrays.asList(testUserDTO, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Test User 2"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void updateUser_Success() throws Exception {
        // Arrange
        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1L);
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("newpassword");
        updatedUser.setRole(User.UserRole.CLIENT);

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        verify(userService, times(1)).updateUser(eq(1L), any(UserDTO.class));
    }

    @Test
    void deleteUser_Success() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void searchUsersByName_Success() throws Exception {
        // Arrange
        List<UserDTO> users = Arrays.asList(testUserDTO);
        when(userService.searchUsersByName("Test")).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users/search")
                .param("keyword", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User"));

        verify(userService, times(1)).searchUsersByName("Test");
    }

    @Test
    void getUserByEmail_Success() throws Exception {
        // Arrange
        when(userService.getUserByEmail("test@example.com")).thenReturn(testUserDTO);

        // Act & Assert
        mockMvc.perform(get("/api/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).getUserByEmail("test@example.com");
    }
}
