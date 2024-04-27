package first_task.com.in.servlets.user_action;

import first_task.com.dto.WorkoutDto;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.service.UserActionService;
import first_task.com.util.ServiceFactory;
import first_task.com.validators.Validators;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет для добавления новой тренировки
 **/
@WebServlet(name = "AddNewWorkoutServlet",
            urlPatterns = "/users/workouts/add",
            description = "Сервлет добавления тренировки")
public class AddNewWorkoutServlet extends HttpServlet {
    private UserActionService userService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        userService = ServiceFactory.buildUserAction();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonString = req.getReader().lines().collect(Collectors.joining());
            WorkoutDto workoutDto = mapper.readValue(jsonString, WorkoutDto.class);

            List<String> errors = Validators.workoutValidator(workoutDto);

            if (!errors.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(errors));
            }

            WorkoutDto answer = userService.addNewWorkout(workoutDto.getUser_id(), workoutDto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().print(mapper.writeValueAsString(answer));
        } catch (NotUniqueWorkoutException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().print(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Что-то пошло не так");
        }
    }
}
