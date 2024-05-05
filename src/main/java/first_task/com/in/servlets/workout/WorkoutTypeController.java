package first_task.com.in.servlets.workout;

import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.service.WorkoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/workoutType")
@Api(value = "/users/{userId}/workoutType", tags = "Users actions on workoutType")
public class WorkoutTypeController {
    private final WorkoutService workoutService;

    @ApiOperation(
            value = "Save new workoutType",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity workoutTypeSave(@RequestBody WorkoutTypeDto workoutType,
                                          BindingResult bindingResult,
                                          @PathVariable int userId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        try {
            WorkoutTypeDto answer = workoutService.saveWorkoutType(userId, workoutType);
            return ResponseEntity.status(CREATED).body(answer);
        } catch (NotUniqueTypeTitleException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ApiOperation(
            value = "Gives all workoutTypes",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping
    public ResponseEntity getWorkoutTypes() {
        return ResponseEntity.ok(workoutService.getAllTypes());
    }
}
