package first_task.com.in.servlets.authorize;

import first_task.com.dto.UserDto;
import first_task.com.exceptions.NotUniqueUserNameException;
import first_task.com.service.AuthenticationService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterUserServlet", urlPatterns = "/register")
public class RegisterUserServlet extends HttpServlet {

    private AuthenticationService authService;
    private ObjectMapper mapper;

    @Override
    public void init(){
        authService = ServiceFactory.buildAuthentication();
        mapper = new ObjectMapper();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");

            String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            UserDto person = mapper.readValue(jsonString, UserDto.class);
            UserDto userDto = authService.registrationProcess(person.getUsername(), person.getPassword());

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(mapper.writeValueAsString(userDto).getBytes());
        } catch (NotUniqueUserNameException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getOutputStream().write(Utils.formJsonErrorMessage(e.getMessage()).getBytes());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getOutputStream().write(Utils.formJsonErrorMessage("Что-то пошло не так").getBytes());
        }
    }
}
