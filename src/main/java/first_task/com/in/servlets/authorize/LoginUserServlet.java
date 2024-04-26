package first_task.com.in.servlets.authorize;

import first_task.com.dto.UserDto;
import first_task.com.service.AuthenticationService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginUserServlet", urlPatterns = "/login")
public class LoginUserServlet extends HttpServlet {
    private AuthenticationService authenticationService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        authenticationService = ServiceFactory.buildAuthentication();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");

            String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JsonNode jsonNode = mapper.readTree(jsonString);

            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();

            Optional<UserDto> userDto = authenticationService.logIn(username, password);

            if (userDto.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getOutputStream().write(mapper.writeValueAsString(userDto.get()).getBytes());
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IOException e) {
            resp.getOutputStream().write(Utils.formJsonErrorMessage("Что-то пошло не так").getBytes());
        }
    }
}
