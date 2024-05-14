package trainingDiary.com.in.controllers.admin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import trainingDiary.com.annotations.AdminAccessCheck;
import trainingDiary.com.dto.AuditDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.service.AuditLogService;
import trainingDiary.com.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import trainingDiary.com.service.WorkoutService;
import trainingDiary.com.util.ApiTags;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor

@OpenAPIDefinition(
        info = @Info(title = "TrainingDiary controllers documentation", description = "Here you can find info about controllers and endpoints", version = "1.0"),
        servers = @Server(url = "http://localhost:8080/trainingDiary-app-springboot-1.0.0", description = "LocalHost"),
        tags = {
                @Tag(name = ApiTags.ADMIN),
                @Tag(name = ApiTags.User)
        })


@RestController
@Tag(name = ApiTags.ADMIN)
@RequestMapping("/admin")
@AdminAccessCheck
public class AdminController {
    private final UserActionService userService;
    private final WorkoutService workoutService;
    private final AuditLogService auditLogService;

    @Operation(tags = ApiTags.ADMIN,
            description = "Will return full information about all users with their workouts description",
            summary = "get all users with workouts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "users and their workouts",
                            content = {
                                    @Content(array = @ArraySchema(
                                            schema = @Schema(implementation = UserDto.class)),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(tags = ApiTags.ADMIN,
            parameters = {
                @Parameter(in = ParameterIn.PATH, name = "userId", description = "Id of user, whose workout will be deleted"),
                @Parameter(in = ParameterIn.PATH, name = "workoutId", description = "Id of workout which will be deleted")
            },
            description = "Will remove from registered user selected workout",
            summary = "delete user workout",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    ),
            }
    )
    @DeleteMapping("/users/{userId}/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable("userId") int userId,
                                              @PathVariable("workoutId") int workoutId) {
        workoutService.deleteWorkout(userId, workoutId);
        return ResponseEntity.status(OK).build();
    }

    @Operation(tags = ApiTags.ADMIN,
            description = "Will return all workouts of registered user",
            parameters = @Parameter(in = ParameterIn.PATH, name = "userId", description = "Id of user, whose workout will be shown"),
            summary = "get all workouts of specific user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "workouts of selected user",
                            content = {
                                    @Content(array = @ArraySchema(
                                            schema = @Schema(implementation = WorkoutDto.class)),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @GetMapping("/users/{userId}")
    public ResponseEntity<ArrayList<WorkoutDto>> getAllUsersWorkouts(@PathVariable("userId") int userId) {
        ArrayList<WorkoutDto> workoutDtos = userService.showAllWorkoutsDateSorted(userId);
        return ResponseEntity.ok(workoutDtos);
    }

    @Operation(tags = ApiTags.ADMIN,
            description = "Will return all audit logs of application",
            summary = "get all audit logs",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "audit logs of app",
                            content = {
                                    @Content(array = @ArraySchema(
                                            schema = @Schema(implementation = AuditDto.class)),
                                            mediaType = "application/json")
                            }
                    ),
            }
    )
    @GetMapping("/audit")
    public ResponseEntity<ArrayList<AuditDto>> getAllAuditLogs() {
        ArrayList<AuditDto> logEntries = auditLogService.getLogEntries();
        return ResponseEntity.ok(logEntries);
    }
}
