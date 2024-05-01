package first_task.com.in.servlets.admin;

import first_task.com.annotations.LogWithDuration;
import first_task.com.annotations.Loggable;
import first_task.com.dto.UserDto;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет, который возвращает всех пользователей и их тренировки
 **/
@Loggable
@WebServlet(name = "AllUsersServlet",
            urlPatterns = "/admin/usersAll",
            description = "Возвращает всех пользователей приложения с их тренировками")
public class AllUsersServlet extends HttpServlet {
    private UserActionService userService;
    private ObjectMapper mapper;

    public AllUsersServlet() {
        this.userService = ServiceFactory.buildUserAction();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init(){
        userService = ServiceFactory.buildUserAction();
        mapper = new ObjectMapper();
    }
    @LogWithDuration
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<UserDto> allUsers = userService.getAllUsers();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(mapper.writeValueAsString(allUsers));
        } catch (Exception e) {
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }
}
