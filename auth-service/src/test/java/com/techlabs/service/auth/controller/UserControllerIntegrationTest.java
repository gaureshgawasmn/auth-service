package com.techlabs.service.auth.controller;

import com.techlabs.service.auth.config.BaseTest;
import com.techlabs.service.auth.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest extends BaseTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static Long id;
    private static User user;

    static {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("test");
        user.setFirstName("Test");
        user.setLastName("User");
    }

    @Test
    @Order(1)
    @DisplayName("Post call to users should create a new user in the database")
    void createUser() throws Exception {
        String response = mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);
        assertNotNull(createdUser.getId());
        id = createdUser.getId();
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
    }

    @Test
    @Order(2)
    @DisplayName("Get call to users/{id} should retrieve the created user from the database")
    void getUserById() throws Exception {
        String response = mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User retrievedUser = objectMapper.readValue(response, User.class);
        assertNotNull(retrievedUser.getId());
        assertEquals(id, retrievedUser.getId());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getLastName(), retrievedUser.getLastName());
    }
}