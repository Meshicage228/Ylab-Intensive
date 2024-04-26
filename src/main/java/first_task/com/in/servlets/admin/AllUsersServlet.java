package first_task.com.in.servlets.admin;

import first_task.com.dto.UserDto;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AllUsersServlet", urlPatterns = "/admin/users")
public class AllUsersServlet extends HttpServlet {
    private UserActionService userService;
    private ObjectMapper mapper;

    @Override
    public void init(){
        userService = ServiceFactory.buildUserAction();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<UserDto> allUsers = userService.getAllUsers();

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(mapper.writeValueAsString(allUsers));
        } catch (Exception e) {
            resp.getOutputStream().write(Utils.formJsonErrorMessage("Что-то пошло не так").getBytes());
        }
    }
}
