package test.servlets;

import first_task.com.dto.UserDto;
import first_task.com.in.controllers.admin.AllUsersServlet;
import first_task.com.service.UserActionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("AllUsersServlet тесты")
@ExtendWith(MockitoExtension.class)
class AllUsersServletTest{
    @Mock
    private UserActionService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private static AllUsersServlet servlet;

    @BeforeAll
    static void setup() {
        servlet = new AllUsersServlet();
    }

    @Test
    @DisplayName("Получение все пользователей")
    void testDoGet() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(1, "Vlad", "123", new ArrayList<>(), "USER"));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(userService.getAllUsers()).thenReturn(users);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        writer.flush();

        String actualResponse = stringWriter.toString();

        String expectedResponse = "[{\"id\":1,\"username\":\"Vlad\",\"password\":\"123\",\"workouts\":[],\"role\":\"USER\"}]";
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(expectedResponse, actualResponse);
    }
}
