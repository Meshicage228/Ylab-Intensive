package test.servlets;

import static org.mockito.Mockito.*;

import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.in.controllers.authorize.LoginUserServlet;
import first_task.com.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@DisplayName("LoginUserServlet тесты")
@ExtendWith(MockitoExtension.class)
class LoginUserServletTest {
    @InjectMocks
    private static LoginUserServlet servlet;
    @Mock
    private static AuthenticationServiceImpl authenticationServiceImpl;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;

    private static LoginUserDto loginUserDto;

    @BeforeAll
    static void setUp() {
        servlet = new LoginUserServlet();
        loginUserDto = LoginUserDto.builder()
                .username("admin")
                .password("admin")
                .build();
    }

    @DisplayName("Успешный вход в аккаунт")
    @Test
    void doPostShouldReturn200() throws IOException {
        UserDto userDto = new UserDto(1, loginUserDto.getUsername(), loginUserDto.getPassword(), new ArrayList<>(), "USER");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(req.getReader()).thenReturn(createReader());
        when(authenticationServiceImpl.logIn(loginUserDto)).thenReturn(Optional.of(userDto));
        when(resp.getWriter()).thenReturn(pw);

        servlet.doPost(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_OK);
    }

    @DisplayName("Не вошел в аккаунт")
    @Test
    void doPostShouldReturn401() throws IOException {
        when(req.getReader()).thenReturn(createReader());
        when(authenticationServiceImpl.logIn(loginUserDto)).thenReturn(Optional.empty());

        servlet.doPost(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static BufferedReader createReader() throws JsonProcessingException {
        String s = new ObjectMapper().writer().writeValueAsString(loginUserDto);
        return new BufferedReader(new StringReader(s));
    }
}
