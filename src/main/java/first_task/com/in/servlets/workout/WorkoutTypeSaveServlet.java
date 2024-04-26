package first_task.com.in.servlets.workout;

import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.service.WorkoutService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WorkoutTypeSaveServlet", urlPatterns = "/workoutType/add")
public class WorkoutTypeSaveServlet extends HttpServlet {
    private WorkoutService workoutService;
    private ObjectMapper mapper;

    @Override
    public void init(){
        workoutService = ServiceFactory.buildWorkout();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        WorkoutTypeDto workoutTypeDto = mapper.readValue(jsonString, WorkoutTypeDto.class);
        try {
            WorkoutTypeDto answer = workoutService.saveWorkoutType(workoutTypeDto.getUser_id() ,workoutTypeDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().write(mapper.writeValueAsString(answer).getBytes());
        } catch (NotUniqueTypeTitleException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getOutputStream().write(Utils.formJsonErrorMessage(e.getMessage()).getBytes());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
