package com.mindhub.todolist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.utils.GetToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetToken getToken;

    @Test
    public void testGetUserAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        String responseContent = mockMvc.perform(get("/api/user/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        UserModel user = objectMapper.readValue(responseContent, new TypeReference<UserModel>() {});

        assertNotNull(user);
        assertEquals("user@user.com", user.getEmail());
    }

    @Test
    public void testGetUserAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        mockMvc.perform(get("/api/user/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetTaskAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        String responseContent = mockMvc.perform(get("/api/task/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.readValue(responseContent, new TypeReference<Task>() {});

        assertNotNull(task);
        assertEquals(Task.TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testGetTaskAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        mockMvc.perform(get("/api/task/3")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

}
