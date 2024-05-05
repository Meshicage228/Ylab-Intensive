package first_task.com.in.controllers.workout;

import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.InappropriateDataException;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.service.WorkoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<WorkoutTypeDto> workoutTypeSave(@RequestBody WorkoutTypeDto workoutType,
                                                          BindingResult bindingResult,
                                                          @PathVariable int userId) throws NotUniqueTypeTitleException, InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }
        WorkoutTypeDto answer = workoutService.saveWorkoutType(userId, workoutType);
        return ResponseEntity.status(CREATED).body(answer);
    }

    @ApiOperation(
            value = "Gives all workoutTypes",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping
    public ResponseEntity<List<WorkoutTypeDto>> getWorkoutTypes() {
        return ResponseEntity.ok(workoutService.getAllTypes());
    }
}
