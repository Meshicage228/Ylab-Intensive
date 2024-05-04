package first_task.com.in.servlets.authorize;

import first_task.com.annotations.Loggable;
import first_task.com.dto.LoginUserDto;
import first_task.com.dto.UserDto;
import first_task.com.service.impl.AuthenticationServiceImpl;
import first_task.com.util.ServiceFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервлет, позволяющий войти в аккаунт уже зарегистрированному пользователю
 **/
@Loggable
@WebServlet(name = "LoginUserServlet",
            urlPatterns = "/login",
            description = "Login зарегистрированному пользователю")
public class LoginUserServlet extends HttpServlet {
    private AuthenticationServiceImpl authenticationServiceImpl;
    private ObjectMapper mapper;

    public LoginUserServlet() {
        this.authenticationServiceImpl = ServiceFactory.buildAuthentication();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() {
        authenticationServiceImpl = ServiceFactory.buildAuthentication();
        mapper = new ObjectMapper();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonString = req.getReader().lines().collect(Collectors.joining());
            LoginUserDto logInUser = mapper.readValue(jsonString, LoginUserDto.class);

            Optional<UserDto> userDto = authenticationServiceImpl.logIn(logInUser);

            if (userDto.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(mapper.writeValueAsString(userDto.get()));
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(mapper.writeValueAsString("Что-то пошло не так"));
        }
    }
}
