package test.controllers;

import java.util.Optional;

import first_task.com.dto.CurrentUser;
import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.in.controllers.authorize.LoginController;
import first_task.com.service.AuthenticationService;
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

@DisplayName("LoginController tests")
@ExtendWith(MockitoExtension.class)
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
    void successLogIn() throws Exception {
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
    void noUserFound() throws Exception {
        when(service.logIn(ArgumentMatchers.any(LoginUserDto.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized());

        verify(service, times(1)).logIn(Mockito.any(LoginUserDto.class));
    }
}