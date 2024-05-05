package first_task.com.in.servlets.user_action;

import first_task.com.annotations.Loggable;
import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutUpdateDto;
import first_task.com.exceptions.NotUniqueWorkoutException;
import first_task.com.service.UserActionService;
import first_task.com.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/workouts")
public class WorkoutActionController {
    private final WorkoutService workoutService;
    private final UserActionService userService;


    @GetMapping
    public ResponseEntity<ArrayList<WorkoutDto>> getAllUsersWorkouts(@PathVariable("userId") int userId) {
     ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);
        return ResponseEntity.ok(workoutDtos);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable("userId") int userId, @PathVariable("workoutId") int workoutId) {
        workoutService.deleteWorkout(userId, workoutId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> saveWorkout(@PathVariable("userId") int userId,
                                                  @Valid @RequestBody WorkoutDto workout,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            WorkoutDto answer = userService.addNewWorkout(userId, workout);
            return ResponseEntity.status(HttpStatus.CREATED).body(answer);
        } catch (NotUniqueWorkoutException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(workout);
        }
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity updateWorkout(@PathVariable("userId") int userId,
                                        @PathVariable("workoutId") int workoutId,
                                        WorkoutUpdateDto workout) {
        WorkoutDto updated = null;

        if(nonNull(workout.getAdditionalInfo())){
            updated = workoutService.changeAdditionalInfo(userId, workoutId, workout.getAdditionalInfo());
        } else if(nonNull(workout.getCaloriesBurned())){
            updated = workoutService.changeCalories(userId, workoutId, workout.getCaloriesBurned());
        } else if (nonNull(workout.getMinuteDuration())) {
            updated = workoutService.changeMinuteDuration(userId, workoutId, workout.getMinuteDuration());
        } else if (nonNull(workout.getTimeOfWorkout())) {
            updated = workoutService.changeDate(userId, workoutId, workout.getTimeOfWorkout());
        }

        return ResponseEntity.ok(updated);
    }
}
