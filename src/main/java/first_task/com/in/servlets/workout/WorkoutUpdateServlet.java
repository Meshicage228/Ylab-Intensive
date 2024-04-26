package first_task.com.in.servlets.workout;

import first_task.com.dto.WorkoutDto;
import first_task.com.service.WorkoutService;
import first_task.com.util.ServiceFactory;
import first_task.com.util.Utils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebServlet(name = "WorkoutUpdateServlet", urlPatterns = "/workout/*")
public class WorkoutUpdateServlet extends HttpServlet {
    private WorkoutService workoutService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        workoutService = ServiceFactory.buildWorkout();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String endpoint = req.getRequestURI();
        String jsonString = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JsonNode jsonNode = mapper.readTree(jsonString);
        String jsonResponse = "";

        Integer user_id = jsonNode.get("user_id").intValue();
        Integer workout_id = jsonNode.get("workout_id").intValue();
        WorkoutDto workoutDto = null;

        switch (endpoint) {
            case "/workout/changeDate" -> {
                String newDate = jsonNode.get("date").textValue();
                workoutDto = workoutService.changeDate(user_id, workout_id, newDate);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            case "/workout/changeAdditionalInfo" -> {
                String addInfo = jsonNode.get("additionalInfo").textValue();
                workoutDto = workoutService.changeAdditionalInfo(user_id, workout_id, addInfo);
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            case "/workout/changeCalories" -> {
                Double calories = jsonNode.get("calories").doubleValue();
                workoutDto = workoutService.changeCalories(user_id, workout_id, calories);
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            case "/workout/changeMinuteDuration" -> {
                Double minutes = jsonNode.get("minutes").doubleValue();
                workoutDto = workoutService.changeMinuteDuration(user_id, workout_id, minutes);
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            default -> {
                jsonResponse = Utils.formJsonErrorMessage("Что-то пошло не так");
            }
        }
        if (nonNull(workoutDto)) {
            jsonResponse = mapper.writeValueAsString(workoutDto);
        }

        resp.setContentType("application/json");
        resp.getOutputStream().write(jsonResponse.getBytes());
    }


}
