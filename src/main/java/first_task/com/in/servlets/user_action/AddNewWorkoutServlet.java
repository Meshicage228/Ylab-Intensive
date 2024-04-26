package first_task.com.in.servlets.user_action;

import first_task.com.dto.WorkoutDto;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//  urlPatterns = "/users/{user_id}/workouts/add"
@WebServlet(name = "AddNewWorkoutServlet", urlPatterns = "/users/1/workouts/add")
public class AddNewWorkoutServlet extends HttpServlet {

    private UserActionService userService;
    private ObjectMapper mapper;

    @Override
    public void init(){
        userService = ServiceFactory.buildUserAction();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");

            String pathInfo = req.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            Integer userId = Integer.parseInt(pathParts[1]);

            String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            WorkoutDto workoutDto = mapper.readValue(jsonString, WorkoutDto.class);
            WorkoutDto answer = userService.addNewWorkout(userId, workoutDto);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(mapper.writeValueAsString(answer).getBytes());
        } catch (NotUniqueWorkoutException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getOutputStream().write(Utils.formJsonErrorMessage(e.getMessage()).getBytes());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getOutputStream().write(Utils.formJsonErrorMessage("Что-то пошло не так").getBytes());
        }
    }
}
