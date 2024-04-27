package first_task.com.in.servlets.authorize;

import first_task.com.annotations.Loggable;
import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.service.AuthenticationService;
import first_task.com.util.ServiceFactory;
import first_task.com.validators.Validators;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет, позволяющий зарегистрироваться в приложении
 **/
@WebServlet(name = "RegisterUserServlet",
            urlPatterns = "/register",
            description = "Сервлет возможности регистрации")
public class RegisterUserServlet extends HttpServlet {
    private AuthenticationService authService;
    private ObjectMapper mapper;

    public RegisterUserServlet() {
        this.authService = ServiceFactory.buildAuthentication();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() {
        authService = ServiceFactory.buildAuthentication();
        mapper = new ObjectMapper();
    }

    @Loggable
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonString = req.getReader().lines().collect(Collectors.joining());
            UserDto person = mapper.readValue(jsonString, UserDto.class);

            List<String> validate = Validators.userDtoValidator(person);
            if (!validate.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(validate));
            }

            UserDto userDto = authService.registrationProcess(person.getUsername(), person.getPassword());

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getOutputStream().write(mapper.writeValueAsString(userDto).getBytes());
        } catch (NotUniqueUserNameException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString("Что-то пошло не так"));
        }
    }
}
