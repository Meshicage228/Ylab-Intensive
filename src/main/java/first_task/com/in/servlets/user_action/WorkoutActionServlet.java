package first_task.com.in.servlets.user_action;

import first_task.com.annotations.Loggable;
import first_task.com.dto.WorkoutDto;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.service.UserActionService;
import first_task.com.service.WorkoutService;
import first_task.com.util.ServiceFactory;
import first_task.com.validators.Validators;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет для действий над тренировкий
 **/
@Loggable
@WebServlet(name = "WorkoutsActions",
            urlPatterns = "/users/workouts",
            description = "Сервлет с действиями над тренировкой")
public class WorkoutActionServlet extends HttpServlet {
    private WorkoutService workoutService;
    private UserActionService userService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        userService = ServiceFactory.buildUserAction();
        workoutService = ServiceFactory.buildWorkout();
        mapper = new ObjectMapper();
    }

    /**
     * Удаление тренировки
     **/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        Integer workoutId = Integer.valueOf(req.getParameter("workoutId"));

        workoutService.deleteWorkout(userId, workoutId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Сохранение новой тренировки
     **/
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Integer userId = Integer.valueOf(req.getParameter("user_id"));

            ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().print(mapper.writeValueAsString(workoutDtos));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Что-то пошло не так");
        }
    }
}
