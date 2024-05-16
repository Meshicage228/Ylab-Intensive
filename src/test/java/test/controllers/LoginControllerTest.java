package test.controllers;

import java.util.Optional;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.dto.LoginUserDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.in.controllers.authorize.LoginController;
import trainingDiary.com.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LoginController.class)
@AutoConfigureMockMvc
class LoginControllerTest {
    private MockMvc mockMvc;

    private LoginUserDto loginUserDto;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void init() {
        loginUserDto = LoginUserDto.builder()
                .username("test")
                .password("test")
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @DisplayName("Success login")
    @Test
    public void successLogIn() throws Exception {
        when(service.logIn(ArgumentMatchers.any(LoginUserDto.class)))
                .thenReturn(Optional.of(new UserDto()));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isOk());

        verify(service, times(1)).logIn(Mockito.any(LoginUserDto.class));
    }

    @DisplayName("Fail while login")
    @Test
    public void noUserFound() throws Exception {
        when(service.logIn(ArgumentMatchers.any(LoginUserDto.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized());

        verify(service, times(1)).logIn(Mockito.any(LoginUserDto.class));
    }
}