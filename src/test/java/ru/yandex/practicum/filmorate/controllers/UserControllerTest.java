package ru.yandex.practicum.filmorate.controllers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    LocalDate testDate = LocalDate.of(2001, 1, 1);

    private final User user2 = User.builder()
            .id(0)
            .email("rgfdearm@yandex.ru")
            .login("bifgglbo")
            .name("")
            .birthday(testDate)
            .build();
    User firstUser = User.builder()
            .id(0)
            .email("asdasdasdx@yandex.ru")
            .login("ksks")
            .name("ksks")
            .birthday(LocalDate.of(2001, 1, 1))
            .build();

    @BeforeAll
    public void createUser() throws Exception {
        this.mockMvc.perform(
             post("/users")
             .content(objectMapper.writeValueAsString(firstUser))
             .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    public void shouldSuccessPostUser() throws Exception {
        this.mockMvc.perform(post("/users")
                                .content(objectMapper.writeValueAsString(user2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("rgfdearm@yandex.ru"))
                .andExpect(jsonPath("$.login").value("bifgglbo"))
                .andExpect(jsonPath("$.name").value("bifgglbo"))
                .andExpect(jsonPath("$.birthday").value("2001-01-01"))
                .andReturn();
    }

    @Test
    public void shouldSuccessUpdateUser() throws Exception {
        User user = User.builder()
                .id(1)
                .email("asdasdasdx@yandex.ru")
                .login("bilbo")
                .name("TestName")
                .birthday(testDate)
                .build();
        this.mockMvc
                .perform(
                        put("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("asdasdasdx@yandex.ru"))
                .andExpect(jsonPath("$.login").value("bilbo"))
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.birthday").value("2001-01-01"))
                .andReturn();
    }

    @Test
    public void shouldSuccessGetUser() throws Exception {
        this.mockMvc.perform(
                        get("/users/1").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("asdasdasdx@yandex.ru"))
                .andExpect(jsonPath("$.login").value("ksks"))
                .andExpect(jsonPath("$.name").value("ksks"))
                .andExpect(jsonPath("$.birthday").value("2001-01-01"))
                .andReturn();
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        this.mockMvc
                .perform(
                        delete("/users/1")
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldSuccessGetAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/users").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAll"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.readerForListOf(User.class).getValueType());

        assertEquals(1, users.size(), String.format("Ожидался firstUser получен %s", users));
    }

    @Test
    public void shouldNotSendErrorWhenDeleteUserWithBadId() throws Exception {
        this.mockMvc
                .perform(
                        delete("/users/-9999")
                )
                .andExpect(status().isNotFound())
                .andReturn();
    }
}