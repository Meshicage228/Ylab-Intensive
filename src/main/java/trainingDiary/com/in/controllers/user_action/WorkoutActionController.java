package trainingDiary.com.in.controllers.user_action;

import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.dto.WorkoutUpdateDto;
import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;
import trainingDiary.com.service.UserActionService;
import trainingDiary.com.service.WorkoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/workouts")
@Api(value = "/users/{userId}/workouts", tags = "Users actions on workouts")
public class WorkoutActionController {
    private final WorkoutService workoutService;
    private final UserActionService userService;

    @ApiOperation(
            value = "Gives all workouts of specific user",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @GetMapping
    public ResponseEntity<ArrayList<WorkoutDto>> getAllUsersWorkouts(@PathVariable("userId") int userId) {
        ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);
        return ResponseEntity.ok(workoutDtos);
    }

    @ApiOperation(
            value = "Delete users workout",
            httpMethod = "DELETE",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable("userId") int userId,
                                              @PathVariable("workoutId") int workoutId) {
        workoutService.deleteWorkout(userId, workoutId);
        return ResponseEntity.status(OK).build();
    }

    @ApiOperation(
            value = "Save specific workout for user",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PostMapping
    public ResponseEntity<WorkoutDto> saveWorkout(@PathVariable("userId") int userId,
                                                  @Valid @RequestBody WorkoutDto workout,
                                                  BindingResult bindingResult) throws NotUniqueWorkoutException, InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }
        WorkoutDto answer = userService.addNewWorkout(userId, workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }

    @ApiOperation(
            value = "Patch update for users workout",
            httpMethod = "PATCH",
            produces = "application/json",
            response = ResponseEntity.class
    )
    @PatchMapping("/{workoutId}")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable("userId") int userId,
                                                    @PathVariable("workoutId") int workoutId,
                                                    WorkoutUpdateDto workout) {
        WorkoutDto updated = null;

        if (nonNull(workout.getAdditionalInfo())) {
            updated = workoutService.changeAdditionalInfo(userId, workoutId, workout.getAdditionalInfo());
        } else if (nonNull(workout.getCaloriesBurned())) {
            updated = workoutService.changeCalories(userId, workoutId, workout.getCaloriesBurned());
        } else if (nonNull(workout.getMinuteDuration())) {
            updated = workoutService.changeMinuteDuration(userId, workoutId, workout.getMinuteDuration());
        } else if (nonNull(workout.getTimeOfWorkout())) {
            updated = workoutService.changeDate(userId, workoutId, workout.getTimeOfWorkout());
        }

        return ResponseEntity.ok(updated);
    }
}
