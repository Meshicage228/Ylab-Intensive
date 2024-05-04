package first_task.com.in.servlets.workout;

import first_task.com.annotations.Loggable;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.service.WorkoutService;
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
 * Сервлет для добавления нового типа тренировки
 **/
@Loggable
@WebServlet(name = "WorkoutTypeSaveServlet",
        urlPatterns = "/workoutType",
        description = "Сервлет добавления нового типа тренировки")
public class WorkoutTypeSaveServlet extends HttpServlet {
    private WorkoutService workoutService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        workoutService = ServiceFactory.buildWorkout();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Integer userId = Integer.valueOf(req.getParameter("user_id"));

        String jsonString = req.getReader().lines().collect(Collectors.joining());
        WorkoutTypeDto workoutTypeDto = mapper.readValue(jsonString, WorkoutTypeDto.class);

        List<String> errors = Validators.workoutTypeValidator(workoutTypeDto);

        if (!errors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(errors));
        }
        try {
            WorkoutTypeDto answer = workoutService.saveWorkoutType(userId, workoutTypeDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().print(mapper.writeValueAsString(answer));
        } catch (NotUniqueTypeTitleException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().print(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Что-то пошло не так");
        }
    }
}
