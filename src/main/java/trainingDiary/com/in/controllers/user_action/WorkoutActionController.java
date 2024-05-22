package trainingDiary.com.in.controllers.user_action;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.auditlogaspectstarter.annotations.UserIsLogInCheck;
import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.dto.WorkoutUpdateDto;
import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;
import trainingDiary.com.service.UserActionService;
import trainingDiary.com.service.WorkoutService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import trainingDiary.com.util.ApiTags;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/workouts")
@UserIsLogInCheck
@Tag(name = ApiTags.User)
public class WorkoutActionController {
    private final WorkoutService workoutService;
    private final UserActionService userService;
    private final CurrentUser currentUser;

    @Operation(tags = ApiTags.User,
            description = "Will return full information about all users workouts",
            summary = "get all workouts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "all workouts",
                            content = {
                                    @Content(array = @ArraySchema(
                                            schema = @Schema(implementation = WorkoutDto.class)),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<ArrayList<WorkoutDto>> getAllUsersWorkouts() {
            ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(currentUser.getId());
        return ResponseEntity.ok(workoutDtos);
    }

    @Operation(tags = ApiTags.User,
            description = "Will delete specific workout",
            parameters = @Parameter(in = ParameterIn.PATH, name = "workoutId", description = "Id of workout to be deleted"),
            summary = "delete selected workout"
    )
    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable("workoutId") int workoutId) {
        workoutService.deleteWorkout(currentUser.getId(), workoutId);
        return ResponseEntity.status(OK).build();
    }

    @Operation(tags = ApiTags.User,
            description = "Will save new workout formed by log in user",
            summary = "save new workout",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "saved workout",
                            content = {
                                    @Content(schema = @Schema(implementation = WorkoutDto.class),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<WorkoutDto> saveWorkout(@Valid @RequestBody WorkoutDto workout,
                                                  BindingResult bindingResult) throws NotUniqueWorkoutException, InappropriateDataException {
        if (bindingResult.hasErrors()) {
            throw new InappropriateDataException(bindingResult);
        }
        WorkoutDto answer = userService.addNewWorkout(currentUser.getId(), workout);
        return ResponseEntity.status(CREATED).body(answer);
    }


    @Operation(tags = ApiTags.User,
            description = "Part-update of workout",
            parameters = @Parameter(in = ParameterIn.PATH, name = "workoutId", description = "Id of workout to be updated"),
            summary = "workout update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "updated workout",
                            content = {
                                    @Content(schema = @Schema(implementation = WorkoutDto.class),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @PatchMapping("/{workoutId}")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable("workoutId") int workoutId,
                                                    WorkoutUpdateDto workout) {
        WorkoutDto updated = null;

        if (nonNull(workout.getAdditionalInfo())) {
            updated = workoutService.changeAdditionalInfo(currentUser.getId(), workoutId, workout.getAdditionalInfo());
        } else if (nonNull(workout.getCaloriesBurned())) {
            updated = workoutService.changeCalories(currentUser.getId(), workoutId, workout.getCaloriesBurned());
        } else if (nonNull(workout.getMinuteDuration())) {
            updated = workoutService.changeMinuteDuration(currentUser.getId(), workoutId, workout.getMinuteDuration());
        } else if (nonNull(workout.getTimeOfWorkout())) {
            updated = workoutService.changeDate(currentUser.getId(), workoutId, workout.getTimeOfWorkout());
        }

        return ResponseEntity.ok(updated);
    }
}
