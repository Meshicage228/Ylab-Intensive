package first_task.com.in.servlets.user_action;

import first_task.com.dto.WorkoutDto;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ShowAllWorkoutsServlet", value = "/users/workouts")
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
            String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JsonNode jsonNode = mapper.readTree(jsonString);

            Integer userId = jsonNode.get("user_id").intValue();

            ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(mapper.writeValueAsString(workoutDtos).getBytes());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getOutputStream().write(Utils.formJsonErrorMessage("Что-то пошло не так").getBytes());
        }
    }
}
