package com.techlabs.service.auth.controller;

import com.techlabs.service.auth.AuthRequest;
import com.techlabs.service.auth.config.BaseTest;
import com.techlabs.service.auth.entity.User;
import com.techlabs.service.auth.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerIntegrationTest extends BaseTest {

    @Autowired
    private UserRepo userRepo;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static User savedUser;

    static {
        savedUser = new User();
        savedUser.setEmail("test2@example.com");
        savedUser.setPassword("test");
        savedUser.setFirstName("Test");
        savedUser.setLastName("User");
    }

    @Test
    @Order(1)
    @DisplayName("If user not found then should return 404")
    void authenticate404() throws Exception {
        savedUser = userRepo.save(savedUser);
        AuthRequest authRequest = AuthRequest.builder().username("not_found@example.com").password("test").build();
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    @DisplayName("If password does not match then should return 401")
    void authenticate401() throws Exception {
        AuthRequest authRequest = AuthRequest.builder().username("test2@example.com").password("wrong_password").build();
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    @DisplayName("If user found and password matches then should return 200")
    void authenticate200() throws Exception {
        AuthRequest authRequest = AuthRequest.builder().username("test2@example.com").password("test").build();
        String response = mockMvc.perform(post("/auth/authenticate")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        User retrievedUser = objectMapper.readValue(response, User.class);
        assertEquals(savedUser.getId(), retrievedUser.getId());
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail());
        assertEquals(savedUser.getFirstName(), retrievedUser.getFirstName());
        assertEquals(savedUser.getLastName(), retrievedUser.getLastName());
    }
}