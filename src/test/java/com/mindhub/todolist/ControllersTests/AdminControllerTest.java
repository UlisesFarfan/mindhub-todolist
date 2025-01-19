package com.mindhub.todolist.ControllersTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.utils.GetToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetToken getToken;

    @Test
    public void testGetAllUsersAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        String responseContent = mockMvc.perform(get("/api/admin/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<UserModel> users = objectMapper.readValue(responseContent, new TypeReference<List<UserModel>>() {});

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals("admin@admin.com", users.get(0).getEmail());
    }

    @Test
    public void testGetAllTasksAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        String responseContent = mockMvc.perform(get("/api/admin/task")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Task> tasks = objectMapper.readValue(responseContent, new TypeReference<List<Task>>() {});

        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(Task.TaskStatus.IN_PROGRESS, tasks.get(0).getStatus());
    }

    @Test
    public void testGetAllUsersAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        mockMvc.perform(get("/api/admin/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllTasksAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        mockMvc.perform(get("/api/admin/task")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

}
