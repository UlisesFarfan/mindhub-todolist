package com.mindhub.todolist.ControllersTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterWithRightData() throws Exception {
        String jsonRequest = "{" +
                "  \"name\": \"admin\"," +
                "  \"email\": \"admin2@admin.com\"," +
                "  \"password\": \"admin\"" +
                "}";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string("Registrado con exito"));
    }

    @Test
    public void testRegisterWithWrongData() throws Exception {
        String jsonRequest = "{" +
                "  \"name\": \"admin\"," +
                "  \"email\": \"adamin@admin.com\"" +
                "}";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Data"));
    }

    @Test
    public void testLoginWithRightData() throws Exception {
        String jsonRequest = "{" +
                "  \"email\": \"admin@admin.com\"," +
                "  \"password\": \"admin1234\"" +
                "}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginWithWrongData() throws Exception {
        String jsonRequest = "{" +
                "  \"email\": \"adamin@admin.com\"" +
                "}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

}
