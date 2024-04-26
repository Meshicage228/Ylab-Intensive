package first_task.com.in.servlets.workout;

import first_task.com.service.WorkoutService;
import first_task.com.util.ServiceFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WorkoutDeleteServlet", urlPatterns = "/workout/delete")
public class WorkoutDeleteServlet extends HttpServlet {
    private WorkoutService workoutService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        workoutService = ServiceFactory.buildWorkout();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JsonNode jsonNode = mapper.readTree(jsonString);

        Integer user_id = jsonNode.get("user_id").intValue();
        Integer workout_id = jsonNode.get("workout_id").intValue();
        workoutService.deleteWorkout(user_id, workout_id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
