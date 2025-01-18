package com.mindhub.todolist.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class GetToken {

    @Autowired
    private MockMvc mockMvc;

    public String getAdminToken() throws Exception {
        String loginRequest = "{ \"email\": \"admin@admin.com\", \"password\": \"admin1234\" }";

        return mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    public String getUserToken() throws Exception {
        String loginRequest = "{ \"email\": \"user@user.com\", \"password\": \"user1234\" }";

        return mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
