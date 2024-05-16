package trainingDiary.com.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import trainingDiary.com.config.TestBeans;
import trainingDiary.com.dto.LoginUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Login tests")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private LoginUserDto loginUserDto;

    @BeforeEach
    public void init(){
        loginUserDto = LoginUserDto.builder()
                .username("Vlad")
                .password("123")
                .build();
    }

    @DisplayName("Success login")
    @Test
    public void successLogIn() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("Fail while login")
    @Test
    public void noUserFound() throws Exception {
        loginUserDto.setUsername("faf");
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized());
    }
}