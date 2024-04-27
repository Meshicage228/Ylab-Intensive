package first_task.com.in.servlets.authorize;

import first_task.com.annotations.Loggable;
import first_task.com.dto.UserDto;
import first_task.com.service.AuthenticationService;
import first_task.com.util.ServiceFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
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
@WebServlet(name = "LoginUserServlet",
            urlPatterns = "/login",
            description = "Login зарегистрированному пользователю")
public class LoginUserServlet extends HttpServlet {
    private AuthenticationService authenticationService;
    private ObjectMapper mapper;

    public LoginUserServlet() {
        this.authenticationService = ServiceFactory.buildAuthentication();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() {
        authenticationService = ServiceFactory.buildAuthentication();
        mapper = new ObjectMapper();
    }

    @Loggable
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonString = req.getReader().lines().collect(Collectors.joining());
            JsonNode jsonNode = mapper.readTree(jsonString);

            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();

            Optional<UserDto> userDto = authenticationService.logIn(username, password);

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
