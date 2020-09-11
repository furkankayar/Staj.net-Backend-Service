package com.service.stajnet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationInMillis;

    @Test
    public void loginTest() throws Exception{
    
        this.mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"username\": \"furkankayar\", \"password\": \"123\" }")
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("username").value("furkankayar"))
                            .andExpect(jsonPath("expiresAt").isString());  
    }

    @Test
    public void registrationTest() throws Exception{

        this.mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\": \"testuser\", \"password\": \"testpass\", \"firstName\":\"test\", \"lastName\": \"user\", \"email\":\"testuser@mail.com\"}")
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("status").value("OK"))
                            .andExpect(jsonPath("timestamp").isString())
                            .andExpect(jsonPath("message").value("Registration successful!"));
    }
}