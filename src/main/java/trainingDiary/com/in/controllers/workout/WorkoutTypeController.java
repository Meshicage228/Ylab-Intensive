package trainingDiary.com.in.controllers.workout;

import trainingDiary.com.annotations.UserIsLogInCheck;
import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueTypeTitleException;
import trainingDiary.com.service.WorkoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/workoutType")
@Api(value = "/users/workoutType", tags = "Users actions on workoutType")
@UserIsLogInCheck
public class WorkoutTypeController {
    private final WorkoutService workoutService;
    private final CurrentUser currentUser;

    @ApiOperation(
            value = "Save new workoutType",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity<WorkoutTypeDto> workoutTypeSave(@RequestBody @Valid WorkoutTypeDto workoutType,
                                                          BindingResult bindingResult) throws NotUniqueTypeTitleException, InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }
        WorkoutTypeDto answer = workoutService.saveWorkoutType(currentUser.getId(), workoutType);
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
