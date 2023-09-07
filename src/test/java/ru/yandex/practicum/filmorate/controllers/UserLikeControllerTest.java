package ru.yandex.practicum.filmorate.controllers;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@AutoConfigureTestDatabase
class UserLikeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    LocalDate testDate = LocalDate.of(2001, 1, 1);

    private final User user1 = User.builder()
            .id(0)
            .email("firstUser@yandex.ru")
            .login("user1")
            .name("")
            .birthday(testDate)
            .build();
    private final User user2 = User.builder()
            .id(0)
            .email("secondUser@yandex.ru")
            .login("user2")
            .name("user2")
            .birthday(LocalDate.of(2001, 1, 1))
            .build();

    private final User user3 = User.builder()
            .id(0)
            .email("thirdUser@yandex.ru")
            .login("user3")
            .name("user3")
            .birthday(LocalDate.of(2001, 1, 1))
            .build();

    @BeforeEach
    public void createUser() throws Exception {
        this.mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        this.mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        this.mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user3))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @BeforeEach
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/1/friends/2"));
        this.mockMvc.perform(delete("/users/2/friends/1"));
        this.mockMvc.perform(delete("/users/1/friends/3"));
        this.mockMvc.perform(delete("/users/2/friends/3"));
    }

    @Test
    public void shouldSuccessDeleteFriend() throws Exception {
        this.mockMvc.perform(put("/users/1/friends/2"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putToFriends"));
        this.mockMvc.perform(put("/users/2/friends/1"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putToFriends"));
        this.mockMvc.perform(delete("/users/2/friends/1"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("unfriending"));

        MvcResult result = mockMvc.perform(get("/users/2/friends").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getFriendsList"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.readerForListOf(User.class).getValueType());

        assertEquals(0, users.size(), String.format("Ожидался 0 получен: %s", users.size()));
    }

    @Test
    public void shouldAddToFriendSuccess() throws Exception {
        this.mockMvc.perform(put("/users/1/friends/2"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putToFriends"));

        MvcResult result = mockMvc.perform(get("/users/1/friends").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getFriendsList"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.readerForListOf(User.class).getValueType());

        assertEquals(1, users.size(), String.format("Ожидался 1 друг получен: %s", users.size()));
    }

    @Test
    public void shouldSuccessReturnCommonFriend() throws Exception {
        this.mockMvc.perform(put("/users/1/friends/3"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putToFriends"));
        this.mockMvc.perform(put("/users/2/friends/3"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("putToFriends"));

        MvcResult result = mockMvc.perform(get("/users/1/friends/common/2").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("listOfMutualFriends"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.readerForListOf(User.class).getValueType());

        assertEquals(1, users.size(), String.format("Ожидался 1 общий друг получен: %s", users.size()));
    }
}

