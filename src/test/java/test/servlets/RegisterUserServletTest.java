package test.servlets;

import static org.mockito.Mockito.*;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import first_task.com.dto.UserDto;
import first_task.com.in.servlets.authorize.RegisterUserServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("RegisterUserServlet тесты")
@ExtendWith(MockitoExtension.class)
class RegisterUserServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private RegisterUserServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new RegisterUserServlet();
    }

    @DisplayName("Регистрация : имя уже существует")
    @Test
    void doPost_Exception() throws IOException {
        UserDto userDto = new UserDto(1,"Test1", "123123", new ArrayList<>(), null);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);
        when(request.getReader()).thenReturn(createBufferReader(userDto));

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
        verify(response).setContentType("application/json");
    }

    private BufferedReader createBufferReader(UserDto userDto) throws JsonProcessingException {
        String jsonString = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(userDto);
        return new BufferedReader(new StringReader(jsonString));
    }

}