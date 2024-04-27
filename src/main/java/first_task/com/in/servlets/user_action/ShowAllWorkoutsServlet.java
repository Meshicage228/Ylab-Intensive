package first_task.com.in.servlets.user_action;

import first_task.com.annotations.Loggable;
import first_task.com.dto.WorkoutDto;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Сервлет всех тренировок конкретного пользователя
 **/
@Loggable
@WebServlet(name = "ShowAllWorkoutsServlet",
            value = "/users/workouts",
            description = "Сервлет тренировок конкретного пользователя")
public class ShowAllWorkoutsServlet extends HttpServlet {
    private UserActionService userService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        userService = ServiceFactory.buildUserAction();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonString = req.getReader().lines().collect(Collectors.joining());
            JsonNode jsonNode = mapper.readTree(jsonString);

            Integer userId = jsonNode.get("user_id").intValue();

            ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().print(mapper.writeValueAsString(workoutDtos));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Что-то пошло не так");
        }
    }
}
