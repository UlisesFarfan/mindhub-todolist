package com.mindhub.todolist.ControllersTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.utils.GetToken;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetToken getToken;

    @Test
    @Order(1)
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
    @Order(2)
    public void testPutUserAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        String updateData = "{" +
                "\t\"name\": \"ULISES\"," +
                "\t\"email\": \"user@user.com\"" +
                "}";

        String responseContent = mockMvc.perform(put("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateData)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        UserModel user = objectMapper.readValue(responseContent, new TypeReference<UserModel>() {});

        assertNotNull(user);
        assertEquals("ULISES", user.getName());
    }

    @Test
    @Order(3)
    public void testGetUserAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        mockMvc.perform(get("/api/user/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
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
    @Order(5)
    public void testGetTaskAsAnAdmin() throws Exception {
        String token = getToken.getAdminToken();

        mockMvc.perform(get("/api/task/3")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(6)
    public void testPostTaskAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        String newTask = "{" +
                "\"title\": \"Terminar los test\"," +
                "\"description\": \"terminar test de user controller\"," +
                "\"status\": \"IN_PROGRESS\"," +
                "\"userModel\": {" +
                "\"id\": 2" +
                "}" +
                "}";

        String responseContent = mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTask)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.readValue(responseContent, new TypeReference<Task>() {});

        assertNotNull(task);
        assertEquals("Terminar los test", task.getTitle());
    }

    @Test
    @Order(7)
    public void testPutTaskAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        String newTask = "{" +
                "\"title\": \"Terminar los test\"," +
                "\"description\": \"terminar test de user controller\"," +
                "\"status\": \"COMPLETED\"" +
                "}";

        String responseContent = mockMvc.perform(put("/api/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTask)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.readValue(responseContent, new TypeReference<Task>() {});

        assertNotNull(task);
        assertEquals(Task.TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    @Order(8)
    public void testDeleteTaskAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        mockMvc.perform(delete("/api/task/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    public void testDeleteUserAsAnUser() throws Exception {
        String token = getToken.getUserToken();

        mockMvc.perform(delete("/api/user/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

}
