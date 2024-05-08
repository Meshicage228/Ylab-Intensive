package test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import first_task.com.dto.LoginUserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.exceptions.handlres.AppExceptionHandler;
import first_task.com.in.controllers.authorize.RegisterController;
import first_task.com.service.AuthenticationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("RegisterController tests")
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppExceptionHandler appExceptionHandler;

    private LoginUserDto loginUserDto;

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private RegisterController controller;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(appExceptionHandler)
                .build();
        loginUserDto = LoginUserDto.builder()
                .username("test")
                .password("test")
                .build();
    }

    @DisplayName("Success registration")
    @Test
    public void authorizeSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Fail while registration")
    @Test
    public void authorizeThrowsException() throws Exception {
        when(service.registrationProcess(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new NotUniqueUserNameException("Not unique username"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserDto)))
                .andExpect(result -> assertInstanceOf(NotUniqueUserNameException.class, result.getResolvedException()));
    }
}