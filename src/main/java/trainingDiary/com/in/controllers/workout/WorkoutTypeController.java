package trainingDiary.com.in.controllers.workout;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import trainingDiary.com.annotations.UserIsLogInCheck;
import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.exceptions.InappropriateDataException;
import trainingDiary.com.exceptions.NotUniqueTypeTitleException;
import trainingDiary.com.service.WorkoutService;
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
@UserIsLogInCheck
public class WorkoutTypeController {
    private final WorkoutService workoutService;
    private final CurrentUser currentUser;

    @Operation(
            description = "Will save new workout type in application",
            summary = "new workout type",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "saved workout type",
                            content = {
                                    @Content(schema = @Schema(implementation = WorkoutTypeDto.class),
                                            mediaType = "application/json")
                            }
                    ),
            }
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

    @Operation(
            description = "Will give all workout types in application",
            summary = "get all workout type",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "all workout types",
                            content = {
                                    @Content(array = @ArraySchema(
                                            schema = @Schema(implementation = WorkoutTypeDto.class)),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<List<WorkoutTypeDto>> getWorkoutTypes() {
        return ResponseEntity.ok(workoutService.getAllTypes());
    }
}
